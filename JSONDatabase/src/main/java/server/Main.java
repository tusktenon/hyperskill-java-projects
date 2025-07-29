package server;

import java.io.IOException;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    private static final String DATA_FILE = "src/server/data/db.json";

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        Database database = Database.load(DATA_FILE);
        Server server = new Server(ADDRESS, PORT, database);
        server.listen();
    }
}
