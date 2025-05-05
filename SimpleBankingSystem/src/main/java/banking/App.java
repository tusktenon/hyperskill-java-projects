package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

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

    private final Connection connection;
    private final Scanner in;
    private boolean exitRequested = false;

    static void run(String dataFile) {
        String url = "jdbc:sqlite:" + dataFile;
        try (Connection connection = DriverManager.getConnection(url);
             Scanner in = new Scanner(System.in)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("""
                        CREATE TABLE IF NOT EXISTS card (
                            id INTEGER,
                            number TEXT,
                            pin TEXT,
                            balance INTEGER DEFAULT 0
                        )
                        """);
            }
            App app = new App(connection, in);
            app.mainMenu();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private App(Connection connection, Scanner in) {
        this.connection = connection;
        this.in = in;
    }

    private void mainMenu() throws SQLException {
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

    private void handleNewAccountRequest() throws SQLException {
        Card card = DatabaseUtils.add(connection);
        System.out.printf("""
                        Your card has been created
                        Your card number:
                        %s
                        Your card PIN:
                        %s
                        """,
                card.number(), card.pin());
    }

    private void handleLoginRequest() throws SQLException {
        System.out.print("Enter your card number: ");
        String cardNumber = in.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = in.nextLine();
        System.out.println();
        DatabaseUtils.lookup(cardNumber, pin, connection)
                .ifPresentOrElse(
                        card -> {
                            System.out.println("You have successfully logged in!");
                            loggedInMenu(card);
                        },
                        () -> System.out.println("Wrong card number or PIN!")
                );
    }

    private void loggedInMenu(Card card) {
        while (true) {
            System.out.print(LOGGED_IN_MENU_TEXT);
            String selection = in.nextLine();
            System.out.println();
            switch (selection) {
                case "1" -> System.out.println("Balance: " + card.balance());
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
