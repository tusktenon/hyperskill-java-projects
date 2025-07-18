package mealplanner;

import java.util.List;

public interface MealDao {

    List<Meal> findByCategory(Meal.Category category);

    void add(Meal meal);
}
