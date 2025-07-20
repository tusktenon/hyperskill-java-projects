package mealplanner;

import java.util.*;

public class InMemoryMealDao implements MealDao {

    private final List<Meal> meals = new ArrayList<>();

    @Override
    public void add(Meal meal) {
        meals.add(meal);
    }

    @Override
    public List<Meal> findByCategory(Meal.Category category) {
        return meals.stream().filter(meal -> meal.category().equals(category)).toList();
    }

    @Override
    public SortedMap<String, Integer> findNamesByCategory(Meal.Category category) {
        TreeMap<String, Integer> names = new TreeMap<>();
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            if (meal.category().equals(category)) {
                names.put(meal.name(), i);
            }
        }
        return names;
    }

    public String findNameById(int id) {
        return meals.get(id).name();
    }
}
