package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import shared.Request;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    @Parameter(names = "-t", required = true, description = "Type of request")
    Request.Type type;

    @Parameter(names = "-i", description = "Cell index")
    Integer index;

    @Parameter(names = "-m", description = "Message to save")
    String message;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        main.run();
    }

    void run() throws IOException {
        try (Socket socket = new Socket(ADDRESS, PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Client started!");
            String request = new Request(type, index, message).toString();
            output.writeUTF(request);
            System.out.println("Sent: " + request);
            String response = input.readUTF();
            System.out.println("Received: " + response);
        }
    }
}
