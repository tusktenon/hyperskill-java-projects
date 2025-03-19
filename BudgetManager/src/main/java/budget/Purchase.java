package budget;

record Purchase(String description, double amount) {
    @Override
    public String toString() {
        return "%s $%.2f".formatted(description, amount);
    }
}
