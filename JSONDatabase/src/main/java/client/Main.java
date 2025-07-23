package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    @Parameter(names = "-t", required = true, description = "Type of request")
    String type;

    @Parameter(names = "-i", description = "Cell index")
    int index;

    @Parameter(names = "-m", description = "Message to save")
    String message;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);
        main.run();
    }

    void run() {
        try (Socket socket = new Socket(ADDRESS, PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Client started!");
            String request = switch (type) {
                case "set" -> "set " + index + " " + message;
                case "get" -> "get " + index;
                case "delete" -> "delete " + index;
                case "exit" -> "exit";
                default -> throw new IllegalArgumentException(
                        "Unknown request type '%s'".formatted(type));
            };
            output.writeUTF(request);
            System.out.println("Sent: " + request);
            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("The client encountered an IO exception");
        }
    }
}
