package mealplanner;

import java.util.*;

public class InMemoryMealDao implements MealDao {

    private final List<Meal> meals = new ArrayList<>();

    @Override
    public List<Meal> findAll() {
        return Collections.unmodifiableList(meals);
    }

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }
}
