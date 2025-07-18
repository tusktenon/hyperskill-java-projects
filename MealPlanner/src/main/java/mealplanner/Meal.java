package mealplanner;

import java.util.ArrayList;
import java.util.List;

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

    public static class Builder {

        private final Category category;
        private final String name;
        private final List<String> ingredients = new ArrayList<>();

        public Builder(Category category, String name) {
            this.category = category;
            this.name = name;
        }

        public void addIngredient(String ingredient) {
            ingredients.add(ingredient);
        }

        public Meal build() {
            return new Meal(category, name, ingredients.toArray(new String[0]));
        }
    }
}
