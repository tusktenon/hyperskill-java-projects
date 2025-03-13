package traffic;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String greeting = "Welcome to the traffic management system!";
    private static final String menu = """
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit
            """;

    private static void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
    }

    private static int getPositiveInteger(Scanner in) {
        while (true) {
            String input = in.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) return value;
                else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.print("Incorrect input. Try again: ");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(greeting);
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Input the number of roads: ");
            getPositiveInteger(in);
            System.out.print("Input the interval: ");
            getPositiveInteger(in);

            while (true) {
                clearConsole();
                System.out.print(menu);
                switch (in.nextLine()) {
                    case "1" -> System.out.println("Road added");
                    case "2" -> System.out.println("Road deleted");
                    case "3" -> System.out.println("System opened");
                    case "0" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Incorrect option");
                }
                in.nextLine();
            }
        }
    }
}
