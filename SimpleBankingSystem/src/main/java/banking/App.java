package banking;

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

    private final AccountsRegistry accounts;
    private final Scanner in;
    private boolean exitRequested = false;

    static void run(AccountsRegistry accounts) {
        try (Scanner in = new Scanner(System.in)) {
            App app = new App(accounts, in);
            app.mainMenu();
        }
    }

    private App(AccountsRegistry accounts, Scanner in) {
        this.accounts = accounts;
        this.in = in;
    }

    private void requestExit() {
        exitRequested = true;
        System.out.println("Bye!");
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
        Account account = accounts.add();
        System.out.printf("""
                        Your card has been created
                        Your card number:
                        %d
                        Your card PIN:
                        %s
                        """,
                account.getCardNumber(), account.getPinString());
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
                            account -> {
                                System.out.println("You have successfully logged in!");
                                loggedInMenu(account);
                            },
                            () -> System.out.println("Wrong card number or PIN!")
                    );
        } catch (NumberFormatException e) {
            System.out.println("Wrong card number or PIN!");
        }
    }

    private void loggedInMenu(Account account) {
        while (true) {
            System.out.print(LOGGED_IN_MENU_TEXT);
            String selection = in.nextLine();
            System.out.println();
            switch (selection) {
                case "1" -> System.out.println("Balance: " + account.getBalance());
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
}
