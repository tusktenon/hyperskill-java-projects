package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import shared.Request;
import shared.Response;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final Database database;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public Server(String address, int port, Database database) throws IOException {
        this.database = database;
        serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
    }

    public void listen() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(() -> handleClient(socket));
            } catch (SocketException ignored) {
                // This is expected; when a ServerSocket is closed, any thread
                // currently blocked in accept() will throw a SocketException.
            }
        }
        executor.shutdown();
    }

    private void handleClient(Socket socket) {
        try (socket;
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String received = input.readUTF();
            System.out.println("Received: " + received);
            Request request = new Gson().fromJson(received, Request.class);
            String responseJson = new Gson().toJson(processRequest(request));
            output.writeUTF(responseJson);
            System.out.println("Sent: " + responseJson);
            if (request.type() == Request.Type.exit) {
                socket.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("The server encountered an exception " +
                    "while handling a client request.");
        }
    }

    private Response processRequest(Request request) throws IOException {
        switch (request.type()) {
            case get -> {
                JsonElement value = database.get(request.key());
                return value == null
                        ? Response.err("No such key")
                        : Response.ok(value);
            }
            case set -> {
                database.set(request.key(), request.value());
                return Response.ok();
            }
            case delete -> {
                JsonElement deleted = database.delete(request.key());
                return deleted == null ? Response.err("No such key") : Response.ok();
                }
            default -> { // case exit
                return Response.ok();
            }
        }
    }
}
