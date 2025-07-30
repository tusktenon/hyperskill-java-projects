package stage4.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Client {

    private final Scanner userInput;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final String dataDirectory;

    public Client(String address, int port, String dataDirectory) throws IOException {
        userInput = new Scanner(System.in);
        socket = new Socket(InetAddress.getByName(address), port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        this.dataDirectory = dataDirectory;
    }

    public void run() throws IOException {
        System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file): ");
        try (userInput; socket; in; out) {
            switch (userInput.nextLine()) {
                case "1" -> {
                    buildAndSendFileReferenceRequest("GET");
                    receiveAndProcessGetResponse();
                }
                case "2" -> {
                    buildAndSendPutRequest();
                    receiveAndDisplayPutResponse();
                }
                case "3" -> {
                    buildAndSendFileReferenceRequest("DELETE");
                    receiveAndDisplayDeleteResponse();
                }
                case "exit" -> {
                    out.writeUTF("EXIT");
                    System.out.println("The request was sent.");
                }
                default -> System.out.println("Invalid action.");
            }
        }
    }

    private void buildAndSendFileReferenceRequest(String method) throws IOException {
        System.out.printf(
                "Do you want to %s the file by name or by id (1 - name, 2 - id): ",
                method.toLowerCase());

        String request = null;
        do {
            switch (userInput.nextLine()) {
                case "1" -> {
                    System.out.print("Enter name: ");
                    String fileName = userInput.nextLine();
                    request = method + " BY_NAME " + fileName;
                }
                case "2" -> {
                    System.out.print("Enter id: ");
                    String fileID = userInput.nextLine();
                    request = method + " BY_ID " + fileID;
                }
                default -> System.out.println("Invalid selection.");
            }
        } while (request == null);

        out.writeUTF(request);
        System.out.println("The request was sent.");
    }

    private void buildAndSendPutRequest() throws IOException {
        System.out.print("Enter name of the file: ");
        String fileName = userInput.nextLine();
        byte[] content;
        if (fileName.isBlank()) {
            System.out.print("Enter file content: ");
            content = userInput.nextLine().getBytes();
        } else {
            content = Files.readAllBytes(Paths.get(dataDirectory, fileName));
        }

        System.out.print("Enter name of the file to be saved on server: ");
        String targetFileName = userInput.nextLine().trim();
        String header = targetFileName.isEmpty() ? "PUT " : "PUT " + targetFileName + ' ';

        out.writeUTF(header);
        out.writeInt(content.length);
        out.write(content);
        System.out.println("The request was sent.");
    }

    private void receiveAndProcessGetResponse() throws IOException {
        String responseHeader = in.readUTF().trim();
        switch (responseHeader) {
            case "200" -> {
                int length = in.readInt();
                byte[] content = new byte[length];
                in.readFully(content);
                System.out.print("The file was downloaded! Specify a name for it: ");
                String fileName = userInput.nextLine();
                File file = new File(dataDirectory, fileName);
                try (var fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(content);
                    System.out.println("File saved on the hard drive!");
                }
            }
            case "404" -> System.out.println("The response says that this file is not found!");
            default -> System.out.println("Unknown server response header: " + responseHeader);
        }
    }

    private void receiveAndDisplayPutResponse() throws IOException {
        String response = in.readUTF();
        String[] tokens = response.split(" ");
        System.out.println(switch (tokens[0]) {
            case "200" -> "Response says that file is saved! ID = " + tokens[1];
            case "403" -> "The response says that creating the file was forbidden!";
            default -> "Unknown server response: " + response;
        });
    }

    private void receiveAndDisplayDeleteResponse() throws IOException {
        String response = in.readUTF();
        System.out.println(switch (response) {
            case "200" -> "The response says that this file was deleted successfully!";
            case "404" -> "The response says that this file is not found!";
            default -> "Unknown server response: " + response;
        });
    }
}
