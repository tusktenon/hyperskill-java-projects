package stage4.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Optional;

class RequestHandler {

    // Because of the unusual design in which clients can send exit requests to the server,
    // a RequestHandler requires a reference to the underlying ServerSocket (to close it)
    private final ServerSocket serverSocket;

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final String[] tokens;
    private final FileRegistry registry;

    static void handle(ServerSocket serverSocket, Socket socket, FileRegistry registry) {
        try {
            new RequestHandler(serverSocket, socket, registry).processRequest();
        } catch (IOException e) {
            System.out.println("The request handler encountered an exception.");
            e.printStackTrace();
        }
    }

    private RequestHandler(ServerSocket serverSocket, Socket socket, FileRegistry registry)
            throws IOException {
        this.serverSocket = serverSocket;
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        tokens = input.readUTF().split(" ");
        this.registry = registry;
    }

    private void processRequest() throws IOException {
        try (socket; input; output) {
            switch (tokens[0]) {
                case "GET" -> handleGetRequest();
                case "PUT" -> handlePutRequest();
                case "DELETE" -> handleDeleteRequest();
                case "EXIT" -> {
                    socket.close();
                    serverSocket.close();
                }
                default -> output.writeUTF("400");
            }
        }
    }

    private void handleGetRequest() throws IOException {
        try {
            if (tokens.length != 3) throw new IllegalArgumentException();
            Optional<FileRegistry.Entry> entry = registry.lookup(tokens[1], tokens[2]);
            if (entry.isPresent()) {
                output.writeUTF("200 ");
                byte[] body = Files.readAllBytes(entry.get().file().toPath());
                output.writeInt(body.length);
                output.write(body);
            } else {
                output.writeUTF("404");
            }
        } catch (IllegalArgumentException e) {
            output.writeUTF("400");
        }
    }

    private void handlePutRequest() throws IOException {
        switch (tokens.length) {
            case 1 -> {
                FileRegistry.Entry entry = registry.add();
                writeRequestBodyToFile(entry.file());
                output.writeUTF("200 " + entry.id());
            }
            case 2 -> {
                Optional<FileRegistry.Entry> entry = registry.add(tokens[1]);
                if (entry.isPresent()) {
                    writeRequestBodyToFile(entry.get().file());
                    output.writeUTF("200 " + entry.get().id());
                } else {
                    output.writeUTF("403");
                }
            }
            default -> output.writeUTF("400");
        }
    }

    private void writeRequestBodyToFile(File file) throws IOException {
        int length = input.readInt();
        byte[] body = new byte[length];
        input.readFully(body);
        Files.write(file.toPath(), body);
    }

    private void handleDeleteRequest() throws IOException {
        try {
            if (tokens.length != 3) throw new IllegalArgumentException();
            Optional<FileRegistry.Entry> entry = registry.delete(tokens[1], tokens[2]);
            if (entry.isPresent()) {
                Files.delete(entry.get().file().toPath());
                output.writeUTF("200");
            } else {
                output.writeUTF("404");
            }
        } catch (IllegalArgumentException e) {
            output.writeUTF("400");
        }
    }
}
