package stage3.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file): ");

        try (Scanner userInput = new Scanner(System.in);
             Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {
            switch (userInput.nextLine()) {
                case "1" -> {
                    System.out.print("Enter filename: ");
                    String fileName = userInput.nextLine();
                    out.writeUTF("GET " + fileName);
                    System.out.println("The request was sent.");
                    displayGetResponse(in.readUTF());
                }
                case "2" -> {
                    System.out.print("Enter filename: ");
                    String fileName = userInput.nextLine();
                    System.out.print("Enter file content: ");
                    String fileContent = userInput.nextLine();
                    out.writeUTF("PUT " + fileName + ' ' + fileContent);
                    System.out.println("The request was sent.");
                    displayPutResponse(in.readUTF());
                }
                case "3" -> {
                    System.out.print("Enter filename: ");
                    String fileName = userInput.nextLine();
                    out.writeUTF("DELETE " + fileName);
                    System.out.println("The request was sent.");
                    displayDeleteResponse(in.readUTF());
                }
                case "exit" -> {
                    out.writeUTF("EXIT");
                    System.out.println("The request was sent.");
                }
                default -> System.out.println("Invalid action");
            }
        } catch (IOException e) {
            System.out.println("The client encountered an exception:");
            e.printStackTrace();
        }
    }

    private static void displayGetResponse(String response) {
        System.out.println(response.startsWith("200 ")
                ? "The content of the file is: " + response.substring(4)
                : response.equals("404")
                ? "The response says that the file was not found!"
                : "Unknown server response: " + response);
    }

    private static void displayPutResponse(String response) {
        System.out.println(switch (response) {
            case "200" -> "The response says that the file was created!";
            case "403" -> "The response says that creating the file was forbidden!";
            default -> "Unknown server response: " + response;

        });
    }

    private static void displayDeleteResponse(String response) {
        System.out.println(switch (response) {
            case "200" -> "The response says that the file was successfully deleted!";
            case "404" -> "The response says that the file was not found!";
            default -> "Unknown server response: " + response;
        });
    }
}
