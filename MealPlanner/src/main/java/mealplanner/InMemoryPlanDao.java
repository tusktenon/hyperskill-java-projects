package mealplanner;

import java.util.*;

import static mealplanner.Meal.Category;

public class InMemoryPlanDao implements PlanDao {

    private static final int DAYS_PER_WEEK = Day.values().length;
    private static final int MEALS_PER_DAY = Category.values().length;

    private final int[] mealIds = new int[DAYS_PER_WEEK * MEALS_PER_DAY];
    private final InMemoryMealDao mealDao;

    public InMemoryPlanDao(InMemoryMealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public Map<PlanKey, String> getMealNames() {
        HashMap<PlanKey, String> map = new HashMap<>(DAYS_PER_WEEK * MEALS_PER_DAY);
        int i = 0;
        for (Day day : Day.values()) {
            for (Category category : Category.values()) {
                map.put(new PlanKey(day, category), mealDao.findNameById(mealIds[i++]));
            }
        }
        return map;
    }

    @Override
    public Map<String, Integer> getIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        for (int id : mealIds) {
            for (String ingredient : mealDao.findIngredientsById(id)) {
                ingredients.merge(ingredient, 1, Integer::sum);
            }
        }
        return ingredients;
    }

    @Override
    public void set(Day day, Category category, int mealId) {
        mealIds[day.ordinal() * DAYS_PER_WEEK + category.ordinal()] = mealId;
    }

    @Override
    public void clear() {
        Arrays.fill(mealIds, 0);
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(mealIds).allMatch(id -> id == 0);
    }
}
