package tracker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");

        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                switch (in.nextLine().trim().toLowerCase()) {
                    case "exit" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    case "" -> System.out.println("No input.");
                    default -> System.out.println("Unknown command!");
                }
            }
        }
    }
}
