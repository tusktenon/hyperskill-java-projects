package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class App {

    private static final String MAIN_MENU_TEXT = """
            
            1. Create an account
            2. Log into account
            0. Exit
            """;
    private static final String LOGGED_IN_MENU_TEXT = """
            
            1. Balance
            2. Add income
            3. Do transfer
            4. Close account
            5. Log out
            0. Exit
            """;

    private final Accounts accounts;
    private final Scanner in;
    private boolean exitRequested = false;

    static void run(String dataFile) {
        String url = "jdbc:sqlite:" + dataFile;
        try (Connection connection = DriverManager.getConnection(url);
             Scanner in = new Scanner(System.in)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("""
                        CREATE TABLE IF NOT EXISTS card (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            number TEXT,
                            pin TEXT,
                            balance INTEGER DEFAULT 0
                        )
                        """);
            }
            App app = new App(connection, in);
            app.mainMenu();
        } catch (SQLException e) {
            System.out.print("""
                    The database encountered a problem during application startup.
                    The application cannot continue.
                    """);
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private App(Connection connection, Scanner in) {
        accounts = new Accounts(connection);
        this.in = in;
    }

    private void mainMenu() {
        while (!exitRequested) {
            System.out.print(MAIN_MENU_TEXT);
            String selection = in.nextLine();
            System.out.println();
            switch (selection) {
                case "1" -> handleNewAccountRequest();
                case "2" -> handleLoginRequest();
                case "0" -> requestExit();
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void handleNewAccountRequest() {
        try {
            Accounts.NewCard card = accounts.addAccount();
            System.out.printf("""
                            Your card has been created
                            Your card number:
                            %s
                            Your card PIN:
                            %s
                            """,
                    card.number(), card.pin());
        } catch (SQLException e) {
            System.out.println("The database encountered a problem during account creation.");
            System.err.println(e.getMessage());
        }
    }

    private void handleLoginRequest() {
        System.out.print("Enter your card number: ");
        String cardNumber = in.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = in.nextLine();
        System.out.println();
        try {
            int id = accounts.lookup(cardNumber, pin);
            if (id > 0) {
                System.out.println("You have successfully logged in!");
                loggedInMenu(id);
            } else {
                System.out.println("Wrong card number or PIN!");
            }
        } catch (SQLException e) {
            System.out.println("The database encountered a problem during account lookup.");
            System.err.println(e.getMessage());
        }
    }

    private void loggedInMenu(int id) {
        while (true) {
            System.out.print(LOGGED_IN_MENU_TEXT);
            String selection = in.nextLine();
            System.out.println();
            switch (selection) {
                case "1" -> handleBalanceInquiry(id);
                case "2" -> handleDepositRequest(id);
                case "3" -> handleTransferRequest(id);
                case "4" -> {
                    handleAccountClosureRequest(id);
                    return;
                }
                case "5" -> {
                    System.out.println("You have successfully logged out!");
                    return;
                }
                case "0" -> {
                    requestExit();
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void handleBalanceInquiry(int id) {
        try {
            System.out.println("Balance: " + accounts.getBalance(id));
        } catch (SQLException e) {
            System.out.println("The database encountered a problem during account balance lookup.");
            System.err.println(e.getMessage());
        }
    }

    private void handleDepositRequest(int id) {
        System.out.println("Enter income:");
        int amount = getPositiveAmount();
        try {
            accounts.deposit(id, amount);
            System.out.println("Income was added!");
        } catch (SQLException e) {
            System.out.println(
                    "The database encountered a problem while attempting to add income.");
            System.err.println(e.getMessage());
        }
    }

    private void handleTransferRequest(int id) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String number = in.nextLine();

        if (!CardUtils.isValidNumber(number)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }

        try {
            int targetId = accounts.lookup(number);
            if (targetId < 0) {
                System.out.println("Such a card does not exist.");
            } else if (targetId == id) {
                System.out.println("You can't transfer money to the same account!");
            } else {
                System.out.println("Enter how much money you want to transfer:");
                int amount = getPositiveAmount();
                int balance = accounts.getBalance(id);
                if (balance < amount) {
                    System.out.println("Not enough money!");
                } else {
                    accounts.transfer(id, targetId, amount);
                    System.out.println("Success!");
                }
            }
        } catch (SQLException e) {
            System.out.println(
                    "The database encountered a problem while attempting to transfer funds.");
            System.err.println(e.getMessage());
        }
    }

    private int getPositiveAmount() {
        int amount = -1;
        do {
            try {
                amount = Integer.parseInt(in.nextLine());
                if (amount < 0) throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a positive whole number:");
            }
        } while (amount < 0);
        return amount;
    }

    private void handleAccountClosureRequest(int id) {
        try {
            accounts.closeAccount(id);
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            System.out.println(
                    "The database encountered a problem while attempting to close the account");
            System.err.println(e.getMessage());
        }
    }

    private void requestExit() {
        exitRequested = true;
        System.out.println("Bye!");
    }
}
