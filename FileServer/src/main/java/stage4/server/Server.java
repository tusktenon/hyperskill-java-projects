package stage4.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {

    private final ServerSocket serverSocket;
    private final FileRegistry registry;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private volatile boolean exitRequested = false;

    Server(ServerSocket serverSocket, FileRegistry registry) {
        this.serverSocket = serverSocket;
        this.registry = registry;
    }

    void listen() {
        while (!exitRequested) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(() -> {
                    if (RequestHandler.handle(socket, registry)) {
                        exitRequested = true;
                        sendFollowUpExitRequest();
                    }
                });
            } catch (IOException e) {
                System.out.println("The server encountered an exception " +
                        "while waiting for an incoming connection.");
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    // By the time exitRequested is set to true in the request handler thread, the listen method
    // has already moved on to the next loop iteration, and is blocked on ServerSocket.accept.
    // We need to send a "dummy" request to allow the loop to continue and listen to terminate.
    void sendFollowUpExitRequest() {
        try (var socket = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
             var out = new DataOutputStream(socket.getOutputStream())
        ) {
            out.writeUTF("SHUTDOWN");
        } catch (IOException ignored) {
        }
    }
}
