package budget;

import java.util.function.Predicate;

public class Reports {
    private final Ledger ledger;

    Reports(Ledger ledger) {
        this.ledger = ledger;
    }

    void allPurchases() {
        purchasesByCondition(purchase -> true);
    }

    void purchasesForCategory(Category category) {
        purchasesByCondition(purchase -> purchase.category() == category);
    }

    private void purchasesByCondition(Predicate<Purchase> condition) {
        ledger.purchases()
                .filter(condition)
                .forEach(purchase -> System.out.printf(
                        "%s $%.2f\n", purchase.description(), purchase.amount()));

        double total = ledger.purchases().filter(condition).mapToDouble(Purchase::amount).sum();
        System.out.printf("Total sum: $%.2f\n", total);
    }
}
