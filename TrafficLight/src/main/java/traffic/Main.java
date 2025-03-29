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

    public static void main(String[] args) throws InterruptedException {
        System.out.println(greeting);
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Input the number of roads: ");
            int roads = ConsoleUtils.getPositiveInteger(in);
            System.out.print("Input the interval: ");
            int interval = ConsoleUtils.getPositiveInteger(in);

            QueueThread thread = new QueueThread(roads, interval);
            thread.setName("QueueThread");
            thread.start();

            while (true) {
                ConsoleUtils.clearConsole();
                System.out.print(menu);
                switch (in.nextLine()) {
                    case "1" -> {
                        System.out.print("Input road name: ");
                        String name = in.nextLine();
                        if (thread.addRoad(name))
                            System.out.println(name + " added!");
                        else
                            System.out.println("Queue is full");
                        in.nextLine();
                    }
                    case "2" -> {
                        thread.deleteRoad().ifPresentOrElse(
                                road -> System.out.println(road + " deleted!"),
                                () -> System.out.println("Queue is empty"));
                        in.nextLine();
                    }
                    case "3" -> {
                        thread.displaySystemState(true);
                        in.nextLine();
                        thread.displaySystemState(false);
                    }
                    case "0" -> {
                        thread.shutdown();
                        thread.join();
                        System.out.println("Bye!");
                        return;
                    }
                    default -> {
                        System.out.println("Incorrect option");
                        in.nextLine();
                    }
                }
            }
        }
    }
}
