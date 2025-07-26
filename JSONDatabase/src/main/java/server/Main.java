package server;

import com.google.gson.*;
import shared.Request;
import shared.Response;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String DATA_FILE = "src/server/data/db.json";

    // Maximum time (in milliseconds) to wait for a new client connection
    // before checking if an exit request has already been sent
    private static final int EXIT_CHECK_DELAY = 500;

    private static final Path dataFilePath = Path.of(System.getProperty("user.dir"), DATA_FILE);
    private static JsonObject database;

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static volatile boolean exitRequested = false;

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        database = new Gson().fromJson(Files.readString(dataFilePath), JsonObject.class);
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            listen(server);
        }
    }

    private static void listen(ServerSocket server) throws IOException {
        server.setSoTimeout(EXIT_CHECK_DELAY);
        while (!exitRequested) {
            try {
                Socket socket = server.accept();
                executor.execute(() -> {
                    if (handleClient(socket)) exitRequested = true;
                });
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                System.err.println("The server encountered an exception " +
                        "while waiting for an incoming request.");
            }
        }
        executor.shutdown();
    }

    private static boolean handleClient(Socket socket) {
        try (socket;
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String received = input.readUTF();
            System.out.println("Received: " + received);
            Request request = new Gson().fromJson(received, Request.class);
            String responseJson = new Gson().toJson(processRequest(request));
            output.writeUTF(responseJson);
            System.out.println("Sent: " + responseJson);
            return request.type() == Request.Type.exit;
        } catch (IOException e) {
            System.err.println("The server encountered an exception " +
                    "while handling a client request.");
            return false;
        }
    }

    private static Response processRequest(Request request) throws IOException {
        switch (request.type()) {
            case get -> {
                readLock.lock();
                JsonElement value = database.get(request.key());
                readLock.unlock();
                return value == null
                        ? Response.err("No such key")
                        : Response.ok(value.getAsString());
            }
            case set -> {
                writeLock.lock();
                database.addProperty(request.key(), request.value());
                writeLock.unlock();
                writeDatabase();
                return Response.ok();
            }
            case delete -> {
                writeLock.lock();
                JsonElement deleted = database.remove(request.key());
                writeLock.unlock();
                if (deleted == null) {
                    return Response.err("No such key");
                } else {
                    writeDatabase();
                    return Response.ok();
                }
            }
            default -> { // case exit
                return Response.ok();
            }
        }
    }

    private static void writeDatabase() throws IOException {
        readLock.lock();
        String contents = new Gson().toJson(database);
        readLock.unlock();
        Files.writeString(dataFilePath, contents, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
