package budget;

import java.util.Scanner;

class Menu {
    private final Budget budget;
    private final Budget.Reports reports;
    private final Scanner scanner;

    private static final String MAIN_MENU = """
            
            Choose your action:
            1) Add income
            2) Add purchase
            3) Show list of purchases
            4) Balance
            0) Exit""";

    private static final String ADD_PURCHASE_MENU;
    private static final String LIST_PURCHASES_MENU;

    static {
        StringBuilder addPurchaseMenu = new StringBuilder("\nChoose the type of purchase\n");
        StringBuilder listPurchasesMenu = new StringBuilder("\nChoose the type of purchases\n");

        int menuOption = 1;
        for (String category : Budget.categories) {
            addPurchaseMenu.append("%d) %s\n".formatted(menuOption, category));
            listPurchasesMenu.append("%d) %s\n".formatted(menuOption, category));
            menuOption++;
        }

        addPurchaseMenu.append(menuOption).append(") Back");
        listPurchasesMenu.append(menuOption).append(") All\n");
        listPurchasesMenu.append(++menuOption).append(") Back");

        ADD_PURCHASE_MENU = addPurchaseMenu.toString();
        LIST_PURCHASES_MENU = listPurchasesMenu.toString();
    }

    Menu(Budget budget, Scanner scanner) {
        this.budget = budget;
        this.reports = budget.new Reports();
        this.scanner = scanner;
    }

    void run() {
        while (true) {
            System.out.println(MAIN_MENU);
            switch (scanner.nextLine()) {
                case "1" -> addIncome();
                case "2" -> runAddPurchaseMenu();
                case "3" -> runListPurchasesMenu();
                case "4" -> showBalance();
                case "0" -> {
                    System.out.println("\nBye!");
                    return;
                }
                default -> System.out.println("Please enter a number between 0 and 4");
            }
        }
    }

    void addIncome() {
        System.out.println("\nEnter income:");
        double amount = Double.parseDouble(scanner.nextLine());
        budget.addIncome(amount);
        System.out.println("Income was added!");
    }

    private void runAddPurchaseMenu() {
        int categories = Budget.categories.size();
        String inputErrorMessage
                = "Please enter a number between 1 and %d".formatted(categories + 1);

        while (true) {
            System.out.println(ADD_PURCHASE_MENU);
            String input = scanner.nextLine();
            try {
                int selection = Integer.parseInt(input);
                if (1 <= selection && selection <= categories)
                    addPurchase(selection - 1);
                else if (selection == categories + 1)
                    return;
                else
                    System.out.println(inputErrorMessage);
            } catch (NumberFormatException e) {
                System.out.println(inputErrorMessage);
            }
        }
    }

    void addPurchase(int category) {
        System.out.println("\nEnter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        double amount = Double.parseDouble(scanner.nextLine());
        budget.addPurchase(category, name, amount);
        System.out.println("Purchase was added!");
    }

    void runListPurchasesMenu() {
        if (!budget.hasPurchases()) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }

        int categories = Budget.categories.size();
        String inputErrorMessage
                = "Please enter a number between 1 and %d".formatted(categories + 2);

        while (true) {
            System.out.println(LIST_PURCHASES_MENU);
            String input = scanner.nextLine();
            try {
                int selection = Integer.parseInt(input);
                if (1 <= selection && selection <= categories)
                    showPurchasesForCategory(selection - 1);
                else if (selection == categories + 1)
                    showAllPurchases();
                else if (selection == categories + 2)
                    return;
                else
                    System.out.println(inputErrorMessage);
            } catch (NumberFormatException e) {
                System.out.println(inputErrorMessage);
            }
        }
    }

    void showPurchasesForCategory(int category) {
        System.out.printf("\n%s:\n", Budget.categories.get(category));
        if (budget.hasPurchases(category))
            System.out.println(reports.purchasesForCategory(category));
        else
            System.out.println("The purchase list is empty!");
    }

    void showAllPurchases() {
        System.out.println("\nAll:");
        System.out.println(reports.allPurchases());
    }

    void showBalance() {
        System.out.printf("\nBalance: $%.2f\n", budget.getBalance());
    }
}
