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
    public List<Meal> findAll() {
        Map<Integer, Meal.Builder> meals = new HashMap<>();
        String selectAllMeals = "SELECT * FROM meals";
        String selectAllIngredients = "SELECT ingredient, meal_id FROM ingredients";
        try {
            try (Statement statement = conn.createStatement();
                 ResultSet mealResults = statement.executeQuery(selectAllMeals)) {
                while (mealResults.next()) {
                    int id = mealResults.getInt("meal_id");
                    String category = mealResults.getString("category");
                    String name = mealResults.getString("meal");
                    meals.put(id, new Meal.Builder(Meal.Category.valueOf(category), name));
                }
            }
            try (Statement statement = conn.createStatement();
                 ResultSet ingredientResults = statement.executeQuery(selectAllIngredients)) {
                while (ingredientResults.next()) {
                    int id = ingredientResults.getInt("meal_id");
                    String ingredient = ingredientResults.getString("ingredient");
                    meals.get(id).addIngredient(ingredient);
                }
            }
            return meals.values().stream().map(Meal.Builder::build).toList();
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while retrieving all meals");
        }
    }

    @Override
    public void add(Meal meal) {
        Integer mealId = null;
        String insertMeal = "INSERT INTO meals (category, meal) VALUES (?, ?)";
        String insertIngredient = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, %d)";
        try {
            try (PreparedStatement mealStatement =
                         conn.prepareStatement(insertMeal, Statement.RETURN_GENERATED_KEYS)) {
                mealStatement.setString(1, meal.category().toString());
                mealStatement.setString(2, meal.name());
                mealStatement.executeUpdate();
                try (ResultSet mealIdResults = mealStatement.getGeneratedKeys()) {
                    if (mealIdResults.next()) mealId = mealIdResults.getInt(1);
                }
            }
            if (mealId == null) {
                throw new RuntimeException("Unable to retrieve auto-generated meal_id key");
            }
            try (PreparedStatement ingredientStatement =
                         conn.prepareStatement(insertIngredient.formatted(mealId))) {
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
