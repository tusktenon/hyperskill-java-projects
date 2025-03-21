package budget;

record Purchase(int category, String description, double amount) {
    @Override
    public String toString() {
        return "%s $%.2f".formatted(description, amount);
    }
}
