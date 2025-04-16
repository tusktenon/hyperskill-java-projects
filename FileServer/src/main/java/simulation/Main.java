package simulation;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        StorageEmulator storage = new StorageEmulator();
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                String command = in.next().trim().toLowerCase();
                String fileName = in.nextLine().trim();
                switch (command) {
                    case "add" -> {
                        if (storage.add(fileName))
                            System.out.println("The file " + fileName + " added successfully");
                        else
                            System.out.println("Cannot add the file " + fileName);
                    }
                    case "get" -> {
                        if (storage.get(fileName))
                            System.out.println("The file " + fileName + " was sent");
                        else
                            System.out.println("The file " + fileName + " not found");
                    }
                    case "delete" -> {
                        if (storage.delete(fileName))
                            System.out.println("The file " + fileName + " was deleted");
                        else
                            System.out.println("The file " + fileName + " not found");
                    }
                    case "exit" -> {
                        return;
                    }
                    default -> System.out.println("Unknown command.");
                }
            }
        }
    }
}
