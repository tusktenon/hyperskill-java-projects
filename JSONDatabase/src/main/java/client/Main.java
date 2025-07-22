package client;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        System.out.println("Client started!");
        try (Socket socket = new Socket(ADDRESS, PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String request = "Give me a record # 12";
            output.writeUTF(request);
            System.out.println("Sent: " + request);
            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            System.err.println("The client encountered an IO exception");
        }
    }
}
