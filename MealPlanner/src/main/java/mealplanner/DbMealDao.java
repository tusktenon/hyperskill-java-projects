package mealplanner;

import java.sql.*;
import java.util.*;

public class DbMealDao implements MealDao {

    public static final String CREATE_TABLE_MEALS = """
            CREATE TABLE IF NOT EXISTS meals (
                meal_id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                category varchar NOT NULL,
                meal varchar NOT NULL
            )""";

    public static final String CREATE_TABLE_INGREDIENTS = """
            CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                ingredient varchar,
                meal_id integer REFERENCES meals
            )""";

    private final Connection conn;

    public DbMealDao(Connection conn) {
        this.conn = conn;
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_MEALS);
            statement.executeUpdate(CREATE_TABLE_INGREDIENTS);
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while creating tables");
        }
    }

    @Override
    public List<Meal> findByCategory(Meal.Category category) {
        String query = """
                SELECT m.meal_id, meal, ingredient
                FROM (SELECT meal_id, meal FROM meals WHERE category = '%s') AS m
                JOIN ingredients AS i
                    ON m.meal_id = i.meal_id
                ORDER BY m.meal_id, i.ingredient_id""".formatted(category);

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(query)) {
            Map<Integer, Meal.Builder> meals = new LinkedHashMap<>();
            while (results.next()) {
                int id = results.getInt("meal_id");
                String name = results.getString("meal");
                String ingredient = results.getString("ingredient");
                meals.computeIfAbsent(id, __ -> new Meal.Builder(category, name))
                        .addIngredient(ingredient);
            }
            return meals.values().stream().map(Meal.Builder::build).toList();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Encountered SQL exception while retrieving meals by category");
        }
    }

    @Override
    public void add(Meal meal) {
        Integer mealID = null;
        String insertMeal = "INSERT INTO meals (category, meal) VALUES (?, ?)";
        String insertIngredient = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, %d)";
        try {
            try (PreparedStatement mealStatement =
                         conn.prepareStatement(insertMeal, Statement.RETURN_GENERATED_KEYS)) {
                mealStatement.setString(1, meal.category().toString());
                mealStatement.setString(2, meal.name());
                mealStatement.executeUpdate();
                try (ResultSet mealIdResults = mealStatement.getGeneratedKeys()) {
                    if (mealIdResults.next()) mealID = mealIdResults.getInt(1);
                }
            }
            if (mealID == null) {
                throw new RuntimeException("Unable to retrieve auto-generated meal_id key");
            }
            try (PreparedStatement ingredientStatement =
                         conn.prepareStatement(insertIngredient.formatted(mealID))) {
                for (String ingredient : meal.ingredients()) {
                    ingredientStatement.setString(1, ingredient);
                    ingredientStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while adding a meal");
        }
    }
}
