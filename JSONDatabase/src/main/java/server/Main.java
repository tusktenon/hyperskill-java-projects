package server;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final Pattern REQUEST_PATTERN = Pattern.compile("Give me a record # (\\d+)");

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
             Socket socket = server.accept();
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String request = input.readUTF();
            System.out.println("Received: " + request);
            Matcher matcher = REQUEST_PATTERN.matcher(request);
            if (matcher.matches()) {
                String response = "A record # " + matcher.group(1) + " was sent!";
                output.writeUTF(response);
                System.out.println("Sent: " + response);
            } else {
                System.err.println("Unknown request");
            }
        } catch (IOException e) {
            System.err.println("The server encountered an IO exception");
        }
    }
}
