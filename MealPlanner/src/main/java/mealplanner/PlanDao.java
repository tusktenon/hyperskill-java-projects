package mealplanner;

import java.util.Map;

public interface PlanDao {

    Map<PlanKey, String> getMealNames();

    Map<String, Integer> getIngredients();

    void set(Day day, Meal.Category category, int mealId);

    void clear();

    boolean isEmpty();
}
