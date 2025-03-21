package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class Budget {
    static final List<String> categories
            = List.of("Food", "Clothes", "Entertainment", "Other");

    private final List<Purchase> purchases = new ArrayList<>();
    private double balance = 0;

    double getBalance() {
        return balance;
    }

    void addIncome(double amount) {
        balance += amount;
    }

    void addPurchase(int category, String description, double amount) {
        purchases.add(new Purchase(category, description, amount));
        balance = Math.max(balance - amount, 0);
    }

    boolean hasPurchases() {
        return !purchases.isEmpty();
    }

    boolean hasPurchases(int category) {
        return purchases.stream().anyMatch(purchase -> purchase.category() == category);
    }

    public class Reports {
        public String allPurchases() {
            return purchasesByCondition(purchase -> true);
        }

        public String purchasesForCategory(int category) {
            return purchasesByCondition(purchase -> purchase.category() == category);
        }

        private String purchasesByCondition(Predicate<Purchase> condition) {
            StringBuilder report = new StringBuilder();
            double total = 0;
            for (Purchase purchase : purchases) {
                if (condition.test(purchase)) {
                    report.append(purchase).append('\n');
                    total += purchase.amount();
                }
            }
            report.append("Total sum: $%.2f\n".formatted(total));
            return report.toString();
        }
    }
}
