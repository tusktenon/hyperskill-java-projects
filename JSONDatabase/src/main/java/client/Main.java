package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.*;
import shared.Request;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String REQUESTS_DIR = "src/client/data/";

    @Parameter(names = "-t", description = "Request type")
    Request.Type type;

    @Parameter(names = "-k", description = "Key")
    String key;

    @Parameter(names = "-v", description = "Value")
    String value;

    @Parameter(names = "-in", description = "Request file")
    String fileName;

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
            String requestJson = buildRequestJson();
            output.writeUTF(requestJson);
            System.out.println("Sent: " + requestJson);
            System.out.println("Received: " + input.readUTF());
        }
    }

    String buildRequestJson() throws IOException {
        if (fileName != null) {
            Path path = Path.of(System.getProperty("user.dir"), REQUESTS_DIR, fileName);
            return Files.readString(path);
        }
        JsonElement keyElement = key == null ? null : new JsonPrimitive(key);
        JsonElement valueElement = value == null ? null : new JsonPrimitive(value);
        Request request = new Request(type, keyElement, valueElement);
        return new Gson().toJson(request);
    }
}
