package budget;

import java.util.Scanner;

abstract class Menu {

    final Scanner scanner;

    Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    void run() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            String input = scanner.nextLine();
            exit = processUserInput(input);
        }
    }

    abstract void displayMenu();

    abstract boolean processUserInput(String input);

    static String categoriesMenuSection() {
        StringBuilder text = new StringBuilder();
        int menuOption = 1;
        for (Category category : Category.values()) {
            text.append(menuOption).append(") ").append(category).append('\n');
            menuOption++;
        }
        return text.toString();
    }
}
