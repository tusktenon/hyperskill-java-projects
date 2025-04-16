package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String REQUEST = "Give me everything you have!";

    public static void main(String[] args) {
        System.out.println("Client started!");
        
        try (Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            output.writeUTF(REQUEST);
            System.out.println("Sent: " + REQUEST);
            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            System.out.println("The client encountered an exception:");
            e.printStackTrace();
        }
    }
}
