package stage3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    // Select a data directory:
    // - the first option is required for successful submission on Hyperskill
    // - the second seems more reasonable for a multi-stage project repository
    // private static final String DATA_DIRECTORY = "src/server/data";
    private static final String DATA_DIRECTORY = "data/stage3/server";

    public static void main(String[] args) {
        // Create data directory if it does not already exist
        new File(DATA_DIRECTORY).mkdirs();

        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String[] request = input.readUTF().split(" ", 3);
                    File file = request.length > 1 ? new File(DATA_DIRECTORY, request[1]) : null;
                    switch (request[0]) {
                        case "GET" -> {
                            if (file != null && file.exists())
                                output.writeUTF("200 " + new String(Files.readAllBytes(file.toPath())));
                            else
                                output.writeUTF("404");
                        }
                        case "PUT" -> {
                            if (file != null && file.createNewFile()) {
                                String content = request.length == 3 ? request[2] : "";
                                Files.writeString(file.toPath(), content);
                                output.writeUTF("200");
                            } else {
                                output.writeUTF("403");
                            }
                        }
                        case "DELETE" ->
                                output.writeUTF(file != null && file.delete() ? "200" : "404");
                        case "EXIT" -> {
                            return;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("The server encountered an exception:");
            e.printStackTrace();
        }
    }
}
