package stage4.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    // Select a directory for file storage:
    // - the first option is required for successful submission on Hyperskill
    // - the second seems more reasonable for a multi-stage project repository
    // private static final String DATA_DIRECTORY = "server/data";
    private static final String DATA_DIRECTORY = "data/stage4/server";

    // Select a location (file) to store the serialized file registry
    // private static final String FILE_REGISTRY_STORAGE = "server/data/.file_registry";
    private static final String FILE_REGISTRY_STORAGE = "data/stage4/server/.file_registry";

    public static void main(String[] args) {
        // Create the data directory if it does not already exist
        try {
            Files.createDirectories(Path.of(DATA_DIRECTORY));
        } catch (Exception e) {
            System.out.println("Unable to create or access the data directory.");
            e.printStackTrace();
            return;
        }

        // Deserialize the file registry if the storage file exists, otherwise create a new one
        FileRegistry registry;
        File registryFile = new File(FILE_REGISTRY_STORAGE);
        if (registryFile.exists()) {
            try {
                registry = FileRegistry.load(registryFile);
            } catch (Exception e) {
                System.out.println("""
                        The server encountered an exception during startup:
                        Unable to deserialize the file registry.
                        """);
                e.printStackTrace();
                return;
            }
        } else {
            registry = new FileRegistry(DATA_DIRECTORY);
        }

        // Run the server
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))
        ) {
            Server server = new Server(serverSocket, registry);
            System.out.println("Server started!");
            server.listen();
        } catch (IOException e) {
            System.out.println("""
                    The server encountered an exception during startup:
                    Unable to establish a server socket.
                    """);
            e.printStackTrace();
        }

        // After server shutdown, save the file registry to the storage file
        try {
            registry.save(registryFile);
        } catch (Exception e) {
            System.out.println("""
                    The server encountered an exception during shutdown:
                    Unable to serialize the file registry.
                    """);
            e.printStackTrace();
        }
    }
}
