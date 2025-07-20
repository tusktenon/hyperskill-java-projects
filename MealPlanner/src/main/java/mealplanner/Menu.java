package mealplanner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

import static mealplanner.Meal.Category;

public class Menu {

    private static final Pattern VALID_NAME = Pattern.compile("[A-Za-z ]+");

    private final Scanner in;
    private final MealDao mealDao;
    private final PlanDao planDao;

    public Menu(Scanner in, MealDao mealDao, PlanDao planDao) {
        this.in = in;
        this.mealDao = mealDao;
        this.planDao = planDao;
    }

    public void run() {
        while (true) {
            System.out.println(
                    "What would you like to do (add, show, plan, list plan, save, exit)?");
            switch (in.nextLine()) {
                case "add" -> addMeal();
                case "show" -> displayMeals();
                case "plan" -> planMeals();
                case "list plan" -> displayPlan();
                case "save" -> saveShoppingList();
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }

    private void addMeal() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        Category category = getCategory();
        System.out.println("Input the meal's name:");
        String name = getName();
        System.out.println("Input the ingredients:");
        String[] ingredients = getIngredients();
        mealDao.add(new Meal(category, name, ingredients));
        System.out.println("The meal has been added!");
    }

    private Category getCategory() {
        while (true) {
            try {
                return Category.valueOf(in.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
    }

    private String getName() {
        while (true) {
            try {
                return trimAndValidate(in.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    private String[] getIngredients() {
        while (true) {
            try {
                return Arrays.stream(in.nextLine().split(","))
                        .map(Menu::trimAndValidate)
                        .toArray(String[]::new);
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    private static String trimAndValidate(String input) {
        input = input.trim();
        if (!VALID_NAME.matcher(input).matches()) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    private void displayMeals() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        Category category = getCategory();
        List<Meal> meals = mealDao.findByCategory(category);
        if (meals.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.println("Category: " + category);
            meals.forEach(meal -> System.out.printf("%nName: %s%nIngredients:%n%s%n",
                    meal.name(), String.join("\n", meal.ingredients())));
            System.out.println();
        }
    }

    private void planMeals() {
        planDao.clear();
        var mealsByCategory = new EnumMap<Category, SortedMap<String, Integer>>(Category.class);
        Arrays.stream(Category.values()).forEach(
                category -> mealsByCategory.put(category, mealDao.findNamesByCategory(category)));
        for (Day day : Day.values()) {
            System.out.println(day);
            for (Category category : Category.values()) {
                setPlanEntry(day, category, mealsByCategory.get(category));
            }
            System.out.printf("Yeah! We planned the meals for %s.%n%n", day);
        }
        displayPlan();
    }

    private void setPlanEntry(Day day, Category category, SortedMap<String, Integer> mealOptions) {
        mealOptions.sequencedKeySet().forEach(System.out::println);
        System.out.printf("Choose the %s for %s from the list above:%n", category, day);
        while (true) {
            Integer selectionId = mealOptions.get(in.nextLine());
            if (selectionId == null) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            } else {
                planDao.set(day, category, selectionId);
                break;
            }
        }
    }

    private void displayPlan() {
        if (planDao.isEmpty()) {
            System.out.println("Database does not contain any meal plans");
            return;
        }
        var mealMap = planDao.getMealNames();
        for (Day day : Day.values()) {
            System.out.println(day);
            for (Category category : Category.values()) {
                System.out.println(
                        category.capitalize() + ": " + mealMap.get(new PlanKey(day, category)));
            }
            System.out.println();
        }
    }

    private void saveShoppingList() {
        if (planDao.isEmpty()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }
        System.out.println("Input a filename:");
        try (PrintWriter out = new PrintWriter(in.nextLine())) {
            planDao.getIngredients().forEach((ingredient, count) -> {
                out.println(ingredient + (count > 1 ? " x" + count : ""));
            });
            System.out.println("Saved!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Encountered an exception while writing the shopping list");
        }
    }
}
