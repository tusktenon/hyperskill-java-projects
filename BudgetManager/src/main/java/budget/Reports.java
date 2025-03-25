package budget;

import java.util.function.Predicate;

public class Reports {
    static void allPurchases(Ledger ledger) {
        purchasesByCondition(ledger, purchase -> true);
    }

    static void purchasesForCategory(Ledger ledger, Category category) {
        purchasesByCondition(ledger, purchase -> purchase.category() == category);
    }

    static private void purchasesByCondition(Ledger ledger, Predicate<Purchase> condition) {
        ledger.purchases()
                .filter(condition)
                .forEach(purchase -> System.out.printf(
                        "%s $%.2f\n", purchase.description(), purchase.amount()));

        double total = ledger.purchases().filter(condition).mapToDouble(Purchase::amount).sum();
        System.out.printf("Total sum: $%.2f\n", total);
    }
}
