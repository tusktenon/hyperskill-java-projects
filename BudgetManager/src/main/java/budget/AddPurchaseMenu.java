package budget;

import java.util.Scanner;

class AddPurchaseMenu extends Menu {

    private final Ledger ledger;

    // For efficiency, keep a static copy of the array of Category instances
    private static final Category[] CATEGORIES = Category.values();

    private static final String MENU_TEXT = "\nChoose the type of purchase\n"
            + categoriesMenuSection()
            + "%d) Back".formatted(CATEGORIES.length + 1);

    AddPurchaseMenu(Scanner scanner, Ledger ledger) {
        super(scanner);
        this.ledger = ledger;
    }

    @Override
    void displayMenu() {
        System.out.println(MENU_TEXT);
    }

    @Override
    boolean processUserInput(String input) {
        try {
            int selection = Integer.parseInt(input);
            if (selection == CATEGORIES.length + 1)
                return true;
            else
                addPurchase(CATEGORIES[selection - 1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("\nPlease enter a number between 1 and " + (CATEGORIES.length + 1));
        }
        return false;
    }

    void addPurchase(Category category) {
        System.out.println("\nEnter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        double amount = Double.parseDouble(scanner.nextLine());
        ledger.addPurchase(category, name, amount);
        System.out.println("Purchase was added!");
    }
}
