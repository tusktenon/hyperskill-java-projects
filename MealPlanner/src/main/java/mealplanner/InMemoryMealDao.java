package mealplanner;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMealDao implements MealDao {

    private final List<Meal> meals = new ArrayList<>();

    @Override
    public List<Meal> findByCategory(Meal.Category category) {
        return meals.stream().filter(meal -> meal.category().equals(category)).toList();
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }
}
