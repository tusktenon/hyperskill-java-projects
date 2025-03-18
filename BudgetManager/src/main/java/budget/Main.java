package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String> purchases = new ArrayList<>();

    private static double getAmount(String purchase) {
        int dollarIndex = purchase.indexOf('$');
        String amountString = purchase.substring(dollarIndex + 1);
        return Double.parseDouble(amountString);
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                purchases.add(in.nextLine());
            }
        }

        double total = 0;
        for (String purchase : purchases) {
            System.out.println(purchase);
            total += getAmount(purchase);
        }
        System.out.printf("Total: $%.2f\n", total);
    }
}
