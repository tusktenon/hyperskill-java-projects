package budget;

import java.util.Scanner;

class AddPurchaseMenu extends Menu {

    private final Ledger ledger;

    private static final String MENU_TEXT;
    private static final String INPUT_ERROR_MESSAGE;

    // For efficiency, keep a static copy of the array of Category instances
    private static final Category[] CATEGORIES = Category.values();

    static {
        MENU_TEXT = "\nChoose the type of purchase\n"
                + categoriesMenuSection()
                + "%d) Back".formatted(CATEGORIES.length + 1);

        INPUT_ERROR_MESSAGE =
                "\nPlease enter a number between 1 and %d".formatted(CATEGORIES.length + 1);
    }

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
            if (1 <= selection && selection <= CATEGORIES.length)
                addPurchase(CATEGORIES[selection - 1]);
            else if (selection == CATEGORIES.length + 1)
                return true;
            else
                System.out.println(INPUT_ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            System.out.println(INPUT_ERROR_MESSAGE);
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
