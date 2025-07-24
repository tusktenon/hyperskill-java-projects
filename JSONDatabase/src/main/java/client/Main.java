package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import shared.Request;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    @Parameter(names = "-t", required = true, description = "Request type")
    Request.Type type;

    @Parameter(names = "-k", description = "Key")
    String key;

    @Parameter(names = "-v", description = "Value")
    String value;

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
            String requestJson = new Gson().toJson(new Request(type, key, value));
            output.writeUTF(requestJson);
            System.out.println("Sent: " + requestJson);
            System.out.println("Received: " + input.readUTF());
        }
    }
}
