package budget;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            Menu menu = new MainMenu(in, new Ledger(), "purchases.txt");
            menu.run();
        }
    }
}
