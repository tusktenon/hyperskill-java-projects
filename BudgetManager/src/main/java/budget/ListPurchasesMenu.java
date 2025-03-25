package budget;

import java.util.Scanner;

class ListPurchasesMenu extends Menu {

    private final Ledger ledger;

    private static final String MENU_TEXT;
    private static final String INPUT_ERROR_MESSAGE;

    // For efficiency, keep a static copy of the array of Category instances
    private static final Category[] CATEGORIES = Category.values();

    static {
        MENU_TEXT = "\nChoose the type of purchases\n"
                + categoriesMenuSection()
                + "%d) All\n".formatted(CATEGORIES.length + 1)
                + "%d) Back".formatted(CATEGORIES.length + 2);

        INPUT_ERROR_MESSAGE =
                "\nPlease enter a number between 1 and %d".formatted(CATEGORIES.length + 2);
    }

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
            if (1 <= selection && selection <= CATEGORIES.length) {
                Category category = CATEGORIES[selection - 1];
                System.out.println("\n" + category + ":");
                if (ledger.hasPurchases(category)) Reports.purchasesForCategory(ledger, category);
                else System.out.println("The purchase list is empty!");
            } else if (selection == CATEGORIES.length + 1) {
                System.out.println("\nAll:");
                Reports.allPurchases(ledger);
            } else if (selection == CATEGORIES.length + 2) {
                return true;
            } else {
                System.out.println(INPUT_ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            System.out.println(INPUT_ERROR_MESSAGE);
        }
        return false;
    }
}
