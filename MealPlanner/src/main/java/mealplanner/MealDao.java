package mealplanner;

import java.util.List;
import java.util.SortedMap;

public interface MealDao {

    void add(Meal meal);

    List<Meal> findByCategory(Meal.Category category);

    SortedMap<String, Integer> findNamesByCategory(Meal.Category category);
}
