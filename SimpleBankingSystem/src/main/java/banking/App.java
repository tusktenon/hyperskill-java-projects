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
            2. Log out
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
            Accounts.NewCard card = accounts.add();
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
            accounts.lookup(cardNumber, pin)
                    .ifPresentOrElse(
                            id -> {
                                System.out.println("You have successfully logged in!");
                                loggedInMenu(id);
                            },
                            () -> System.out.println("Wrong card number or PIN!")
                    );
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
                case "1" -> {
                    try {
                        System.out.println("Balance: " + accounts.getBalance(id));
                    } catch (SQLException e) {
                        System.out.println("The database encountered a problem" +
                                " during account balance lookup.");
                        System.err.println(e.getMessage());
                    }
                }
                case "2" -> {
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

    private void requestExit() {
        exitRequested = true;
        System.out.println("Bye!");
    }
}
