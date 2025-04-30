package stage4.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Optional;

class RequestHandler {

    private final FileRegistry registry;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final String[] tokens;
    private volatile boolean isExitRequest = false;

    static boolean handle(Socket socket, FileRegistry registry) {
        try (socket;
             var input = new DataInputStream(socket.getInputStream());
             var output = new DataOutputStream(socket.getOutputStream())
        ) {
            String[] tokens = input.readUTF().split(" ");
            RequestHandler handler = new RequestHandler(tokens, input, output, registry);
            handler.processRequest();
            return handler.isExitRequest;
        } catch (IOException e) {
            System.out.println("The request handler encountered an exception.");
            e.printStackTrace();
            return false;
        }
    }

    private RequestHandler(String[] tokens, DataInputStream input, DataOutputStream output,
                           FileRegistry registry) {
        this.tokens = tokens;
        this.input = input;
        this.output = output;
        this.registry = registry;
    }

    private void processRequest() throws IOException {
        switch (tokens[0]) {
            case "GET" -> handleGetRequest();
            case "PUT" -> handlePutRequest();
            case "DELETE" -> handleDeleteRequest();
            case "EXIT" -> isExitRequest = true;
            default -> output.writeUTF("400");
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
