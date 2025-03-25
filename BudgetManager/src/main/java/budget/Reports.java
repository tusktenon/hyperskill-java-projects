package budget;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class Reports {

    static void allPurchases(Ledger ledger, boolean sorted) {
        System.out.println("\nAll:");
        purchasesByCondition(ledger, purchase -> true, sorted);
    }

    static void purchasesForCategory(Ledger ledger, Category category, boolean sorted) {
        System.out.println("\n" + category + ":");
        purchasesByCondition(ledger, purchase -> purchase.category() == category, sorted);
    }

    static private void purchasesByCondition(Ledger ledger, Predicate<Purchase> condition,
                                             boolean sorted) {
        Purchase[] filtered = ledger.purchases().filter(condition).toArray(Purchase[]::new);
        if (sorted) Arrays.sort(filtered, Comparator.comparingDouble(Purchase::amount).reversed());

        double total = 0;
        for (Purchase purchase : filtered) {
            System.out.printf("%s $%.2f\n", purchase.description(), purchase.amount());
            total += purchase.amount();
        }
        System.out.printf("Total sum: $%.2f\n", total);
    }

    static void categoriesSummary(Ledger ledger) {
        Category[] categories = Category.values();
        double[] categoryTotals = new double[categories.length];

        ledger.purchases().forEach(
                purchase -> categoryTotals[purchase.category().ordinal()] += purchase.amount());

        System.out.println("\nTypes:");
        for (int i = 0; i < categories.length; i++)
            System.out.printf("%s - $%.2f\n", categories[i], categoryTotals[i]);
        System.out.printf("Total sum: $%.2f\n", Arrays.stream(categoryTotals).sum());
    }
}
