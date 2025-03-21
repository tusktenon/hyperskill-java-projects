package budget;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            Menu menu = new Menu(new Budget(), in);
            menu.run();
        }
    }
}
