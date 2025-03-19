package budget;

import java.util.ArrayList;
import java.util.List;

class Budget {
    private final List<Purchase> purchases = new ArrayList<>();
    private double balance = 0;

    void addIncome(double amount) {
        balance += amount;
    }

    void addPurchase(String description, double amount) {
        purchases.add(new Purchase(description, amount));
        balance = Math.max(balance - amount, 0);
    }

    void printBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
    }

    void printPurchases() {
        if (purchases.isEmpty()) {
            System.out.println("The purchase list is empty");
        } else {
            purchases.forEach(System.out::println);
            double total = purchases.stream().mapToDouble(Purchase::amount).sum();
            System.out.printf("Total sum: $%.2f\n", total);
        }
    }
}
