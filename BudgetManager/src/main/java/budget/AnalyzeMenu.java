package budget;

import java.util.Scanner;

public class AnalyzeMenu extends Menu {

    private final Ledger ledger;
    private final SortTypeMenu sortTypeMenu;

    private static final String MENU_TEXT = """
            
            How do you want to sort?
            1) Sort all purchases
            2) Sort by type
            3) Sort certain type
            4) Back""";

    AnalyzeMenu(Scanner scanner, Ledger ledger) {
        super(scanner);
        this.ledger = ledger;
        this.sortTypeMenu = new SortTypeMenu(scanner, ledger);
    }

    @Override
    void displayMenu() {
        System.out.println(MENU_TEXT);
    }

    @Override
    boolean processUserInput(String input) {
        switch (input) {
            case "1" -> {
                if (ledger.hasPurchases()) Reports.allPurchases(ledger, true);
                else System.out.println("\nThe purchase list is empty!");
            }
            case "2" -> Reports.categoriesSummary(ledger);
            case "3" -> sortTypeMenu.run();
            case "4" -> {
                return true;
            }
            default -> System.out.println("\nPlease enter a number between 1 and 4");
        }
        return false;
    }
}
