package server;

import java.io.*;
import java.net.*;

public class Main {

    private record Response(String text, boolean requestExit) {}

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    private static final String[] data = new String[1000];

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            for (boolean exit = false; !exit; ) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                    String request = input.readUTF();
                    System.out.println("Received: " + request);
                    Response response = processRequest(request);
                    output.writeUTF(response.text());
                    System.out.println("Sent: " + response.text());
                    exit = response.requestExit();
                }
            }
        } catch (IOException e) {
            System.out.println("The server encountered an IO exception");
        }
    }

    private static Response processRequest(String request) {
        String responseText = "OK";
        boolean exitRequested = false;
        String[] tokens = request.split("\\s+", 3);
        try {
            int index = tokens.length > 1 ? Integer.parseInt(tokens[1]) : 0;
            if ("set".equals(tokens[0]) && tokens.length == 3) {
                data[index - 1] = tokens[2];
            } else if ("get".equals(tokens[0]) && tokens.length == 2) {
                String content = data[index - 1];
                responseText = content == null ? "ERROR" : content;
            } else if ("delete".equals(tokens[0]) && tokens.length == 2) {
                data[index - 1] = null;
            } else if ("exit".equals(tokens[0]) && tokens.length == 1) {
                exitRequested = true;
            } else {
                responseText = "ERROR";
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            responseText = "ERROR";
        }
        return new Response(responseText, exitRequested);
    }
}
