package traffic;

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

    public static void main(String[] args) {
        System.out.println(greeting);
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Input the number of roads: > ");
            in.nextLine();
            System.out.print("Input the interval: > ");
            in.nextLine();

            while (true) {
                System.out.print(menu);
                switch (in.nextLine()) {
                    case "1" -> System.out.println("Road added");
                    case "2" -> System.out.println("Road deleted");
                    case "3" -> System.out.println("System opened");
                    case "0" -> {
                        System.out.println("Bye!");
                        return;
                    }
                }
            }
        }
    }
}
