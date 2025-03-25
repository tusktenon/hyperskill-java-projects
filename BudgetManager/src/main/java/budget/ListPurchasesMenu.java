package budget;

import java.util.Scanner;

class ListPurchasesMenu extends Menu {

    private final Ledger ledger;

    // For efficiency, keep a static copy of the array of Category instances
    private static final Category[] CATEGORIES = Category.values();

    private static final String MENU_TEXT = "\nChoose the type of purchases\n"
            + categoriesMenuSection()
            + "%d) All\n".formatted(CATEGORIES.length + 1)
            + "%d) Back".formatted(CATEGORIES.length + 2);

    ListPurchasesMenu(Scanner scanner, Ledger ledger) {
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
            if (selection == CATEGORIES.length + 1) {
                Reports.allPurchases(ledger, false);
            } else if (selection == CATEGORIES.length + 2) {
                return true;
            } else {
                Category category = CATEGORIES[selection - 1];
                if (ledger.hasPurchases(category))
                    Reports.purchasesForCategory(ledger, category, false);
                else
                    System.out.println("\nThe purchase list is empty!");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("\nPlease enter a number between 1 and " + (CATEGORIES.length + 2));
        }
        return false;
    }
}
