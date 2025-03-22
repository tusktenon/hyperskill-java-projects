package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class Ledger {
    private final List<Purchase> purchases = new ArrayList<>();
    private double balance = 0;

    double balance() {
        return balance;
    }

    Stream<Purchase> purchases() {
        return purchases.stream();
    }

    void addIncome(double amount) {
        balance += amount;
    }

    void addPurchase(Category category, String description, double amount) {
        purchases.add(new Purchase(category, description, amount));
        balance = Math.max(balance - amount, 0);
    }

    boolean hasPurchases() {
        return !purchases.isEmpty();
    }

    boolean hasPurchases(Category category) {
        return purchases.stream().anyMatch(purchase -> purchase.category() == category);
    }
}
