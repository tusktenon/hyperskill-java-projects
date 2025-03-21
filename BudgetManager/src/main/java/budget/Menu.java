package budget;

import java.util.Scanner;

class Menu {
    private final Budget budget;
    private final Scanner scanner;

    private static final String MENU = """
            
            Choose your action:
            1) Add income
            2) Add purchase
            3) Show list of purchases
            4) Balance
            0) Exit""";

    Menu(Budget budget, Scanner scanner) {
        this.budget = budget;
        this.scanner = scanner;
    }

    void run() {
        while (true) {
            System.out.println(MENU);
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.println("\nEnter income:");
                    double amount = Double.parseDouble(scanner.nextLine());
                    budget.addIncome(amount);
                    System.out.println("Income was added!");
                }
                case "2" -> {
                    System.out.println("\nEnter purchase name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double amount = Double.parseDouble(scanner.nextLine());
                    budget.addPurchase(name, amount);
                    System.out.println("Purchase was added!");
                }
                case "3" -> {
                    System.out.println();
                    budget.printPurchases();
                }
                case "4" -> {
                    System.out.println();
                    budget.printBalance();
                }
                case "0" -> {
                    System.out.println("\nBye!");
                    return;
                }
            }
        }
    }
}
