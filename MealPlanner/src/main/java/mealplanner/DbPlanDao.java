package mealplanner;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static mealplanner.Meal.Category;

public class DbPlanDao implements PlanDao {

    private static final String CREATE_TABLE_PLAN = """
            CREATE TABLE IF NOT EXISTS plan (
                meal_option integer NOT NULL,
                meal_category integer NOT NULL,
                meal_id integer REFERENCES meals
            )""";

    private final Connection conn;

    public DbPlanDao(Connection conn) {
        this.conn = conn;
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_PLAN);
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while creating table");
        }
    }

    @Override
    public Map<PlanKey, String> getMealNames() {
        String query = """
                SELECT meal_option, meal_category, meal
                FROM plan JOIN meals
                    ON plan.meal_id = meals.meal_id
                """;

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(query)) {
            Day[] days = Day.values();
            Category[] categories = Category.values();
            Map<PlanKey, String> names = new HashMap<>(days.length * categories.length);
            while (results.next()) {
                Day day = days[results.getInt("meal_option")];
                Category category = categories[results.getInt("meal_category")];
                String name = results.getString("meal");
                names.put(new PlanKey(day, category), name);
            }
            return names;
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while retrieving meal plan");
        }
    }

    @Override
    public void set(Day day, Category category, int mealId) {
        String sql = "INSERT INTO plan (meal_option, meal_category, meal_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, day.ordinal());
            statement.setInt(2, category.ordinal());
            statement.setInt(3, mealId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while adding plan entry");
        }
    }

    @Override
    public void clear() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM plan");
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while clearing plan");
        }
    }

    @Override
    public boolean isEmpty() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM plan")) {
            results.next();
            return results.getInt(1) == 0;
        } catch (SQLException e) {
            throw new RuntimeException("Encountered SQL exception while clearing plan");
        }
    }
}
