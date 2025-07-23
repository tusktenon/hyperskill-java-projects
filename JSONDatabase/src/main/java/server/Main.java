package server;

import shared.Request;

import java.io.*;
import java.net.*;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    private static final String[] data = new String[1000];

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                    String received = input.readUTF();
                    System.out.println("Received: " + received);
                    Request request = Request.parse(received);
                    String response = processRequest(request);
                    output.writeUTF(response);
                    System.out.println("Sent: " + response);
                    if (request.type() == Request.Type.exit) break;
                } catch (Request.RequestFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private static String processRequest(Request request) {
        String response = "OK";
        try {
            switch (request.type()) {
                case get -> {
                    String content = data[request.index() - 1];
                    response = content == null ? "ERROR" : content;
                }
                case set -> data[request.index() - 1] = request.content();
                case delete -> data[request.index() - 1] = null;
                case exit -> {
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            response = "ERROR";
        }
        return response;
    }
}
