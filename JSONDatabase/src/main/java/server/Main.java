package server;

import com.google.gson.*;
import shared.Request;
import shared.Response;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String DATA_FILE = "src/server/data/db.json";

    private static final Path dataFilePath = Path.of(System.getProperty("user.dir"), DATA_FILE);
    private static JsonObject database;

    public static void main(String[] args) throws IOException {
        database = new Gson().fromJson(Files.readString(dataFilePath), JsonObject.class);
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            listen(server);
        }
    }

    private static void listen(ServerSocket server) throws IOException {
        while (true) {
            try (Socket socket = server.accept();
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                String received = input.readUTF();
                System.out.println("Received: " + received);
                Request request = new Gson().fromJson(received, Request.class);
                String responseJson = new Gson().toJson(processRequest(request));
                output.writeUTF(responseJson);
                System.out.println("Sent: " + responseJson);
                if (request.type() == Request.Type.exit) break;
            }
        }
    }

    private static Response processRequest(Request request) throws IOException {
        switch (request.type()) {
            case get -> {
                JsonElement value = database.get(request.key());
                return value == null
                        ? Response.err("No such key")
                        : Response.ok(value.getAsString());
            }
            case set -> {
                database.addProperty(request.key(), request.value());
                writeDatabase();
                return Response.ok();
            }
            case delete -> {
                JsonElement deleted = database.remove(request.key());
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
        String contents = new Gson().toJson(database);
        Files.writeString(dataFilePath, contents, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
