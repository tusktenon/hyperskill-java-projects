package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String RESPONSE = "All files were sent!";

    public static void main(String[] args) {
        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            try (Socket socket = server.accept();
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            ) {
                String request = input.readUTF();
                System.out.println("Received: " + request);
                output.writeUTF(RESPONSE);
                System.out.println("Sent: " + RESPONSE);
            }
        } catch (IOException e) {
            System.out.println("The server encountered an exception:");
            e.printStackTrace();
        }
    }
}