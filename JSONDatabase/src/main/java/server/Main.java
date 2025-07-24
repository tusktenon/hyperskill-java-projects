package server;

import com.google.gson.Gson;
import shared.Request;
import shared.Response;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    private static final Map<String, String> database = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
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
    }

    private static Response processRequest(Request request) {
        switch (request.type()) {
            case get -> {
                String value = database.get(request.key());
                return value == null ? Response.err("No such key") : Response.ok(value);
            }
            case set -> {
                database.put(request.key(), request.value());
                return Response.ok();
            }
            case delete -> {
                String deleted = database.remove(request.key());
                return deleted == null ? Response.err("No such key") : Response.ok();
            }
            default -> { // case exit
                return Response.ok();
            }
        }
    }
}
