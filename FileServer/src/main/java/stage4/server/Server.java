package stage4.server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {

    private final ServerSocket serverSocket;
    private final FileRegistry registry;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    Server(ServerSocket serverSocket, FileRegistry registry) {
        this.serverSocket = serverSocket;
        this.registry = registry;
    }

    void listen() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(() -> RequestHandler.handle(serverSocket, socket, registry));
            } catch (SocketException ignored) {
                // This is expected; when a ServerSocket is closed, any thread
                // currently blocked in accept() will throw a SocketException.
            } catch (IOException e) {
                System.out.println("The server encountered an exception " +
                        "while waiting for an incoming connection.");
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
