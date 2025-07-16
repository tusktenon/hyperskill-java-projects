package mealplanner;

public record Meal(Category category, String name, String[] ingredients) {

    public enum Category {breakfast, lunch, dinner}

    @Override
    public String toString() {
        return """
                Category: %s
                Name: %s
                Ingredients:
                %s""".formatted(category, name, String.join("\n", ingredients));
    }
}
