package budget;

import java.util.Scanner;

public class SortTypeMenu extends Menu {

    private final Ledger ledger;

    private static final String MENU_TEXT =
            "\nChoose the type of purchase\n" + categoriesMenuSection();

    // For efficiency, keep a static copy of the array of Category instances
    private static final Category[] CATEGORIES = Category.values();

    SortTypeMenu(Scanner scanner, Ledger ledger) {
        super(scanner);
        this.ledger = ledger;
    }

    @Override
    void displayMenu() {
        System.out.print(MENU_TEXT);
    }

    @Override
    boolean processUserInput(String input) {
        try {
            int selection = Integer.parseInt(input);
            Category category = CATEGORIES[selection - 1];
            if (ledger.hasPurchases(category))
                Reports.purchasesForCategory(ledger, category, true);
            else
                System.out.println("\nThe purchase list is empty!");
            return true;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("\nPlease enter a number between 1 and " + CATEGORIES.length);
            return false;
        }
    }
}
