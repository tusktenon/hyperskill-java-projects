package mealplanner;

import java.util.List;

public interface MealDao {

    List<Meal> findAll();

    void add(Meal meal);
}
