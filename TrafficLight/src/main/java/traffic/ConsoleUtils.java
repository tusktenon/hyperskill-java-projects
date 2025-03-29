package traffic;

import java.io.IOException;
import java.util.Scanner;

class ConsoleUtils {

    static void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {
        }
    }

    static int getPositiveInteger(Scanner in) {
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
}
