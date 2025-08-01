package stage4.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    // Select a directory for file storage:
    // - the first option is required for successful submission on Hyperskill
    // - the second seems more reasonable for a multi-stage project repository
    // private static final String DATA_DIRECTORY = "src/client/data";
    private static final String DATA_DIRECTORY = "data/stage4/client";

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of(DATA_DIRECTORY));
        new Client(ADDRESS, PORT, DATA_DIRECTORY).run();
    }
}
