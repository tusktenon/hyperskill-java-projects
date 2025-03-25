package budget;

import java.util.Scanner;

class MainMenu extends Menu {

    private final Ledger ledger;
    private final String dataFile;
    private final AddPurchaseMenu addPurchaseMenu;
    private final ListPurchasesMenu listPurchasesMenu;
    private final AnalyzeMenu analyzeMenu;

    private static final String MENU_TEXT = """
            
            Choose your action:
            1) Add income
            2) Add purchase
            3) Show list of purchases
            4) Balance
            5) Save
            6) Load
            7) Analyze (Sort)
            0) Exit""";

    MainMenu(Scanner scanner, Ledger ledger, String dataFile) {
        super(scanner);
        this.ledger = ledger;
        this.dataFile = dataFile;
        this.addPurchaseMenu = new AddPurchaseMenu(scanner, ledger);
        this.listPurchasesMenu = new ListPurchasesMenu(scanner, ledger);
        this.analyzeMenu = new AnalyzeMenu(scanner, ledger);
    }

    @Override
    void displayMenu() {
        System.out.println(MENU_TEXT);
    }

    @Override
    boolean processUserInput(String input) {
        switch (input) {
            case "1" -> addIncome();
            case "2" -> addPurchaseMenu.run();
            case "3" -> {
                if (ledger.hasPurchases()) listPurchasesMenu.run();
                else System.out.println("\nThe purchase list is empty!");
            }
            case "4" -> System.out.printf("\nBalance: $%.2f\n", ledger.balance());
            case "5" -> {
                FileOps.save(ledger, dataFile);
                System.out.println("\nPurchases were saved!");
            }
            case "6" -> {
                FileOps.load(ledger, dataFile);
                System.out.println("\nPurchases were loaded!");
            }
            case "7" -> analyzeMenu.run();
            case "0" -> {
                System.out.println("\nBye!");
                return true;
            }
            default -> System.out.println("\nPlease enter a number between 0 and 7");
        }
        return false;
    }

    void addIncome() {
        System.out.println("\nEnter income:");
        double amount = Double.parseDouble(scanner.nextLine());
        ledger.addIncome(amount);
        System.out.println("Income was added!");
    }
}
