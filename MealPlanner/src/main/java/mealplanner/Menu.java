package mealplanner;

import java.util.*;
import java.util.regex.Pattern;

import static mealplanner.Meal.Category;

public class Menu {

    private static final Pattern VALID_NAME = Pattern.compile("[A-Za-z ]+");

    private final Scanner in;
    private final MealDao mealDao;

    public Menu(Scanner in, MealDao mealDao) {
        this.in = in;
        this.mealDao = mealDao;
    }

    public void run() {
        while (true) {
            System.out.println("What would you like to do (add, show, exit)?");
            switch (in.nextLine()) {
                case "add" -> addMeal();
                case "show" -> displayMeals();
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }

    private void addMeal() {
        Category category = getCategory();
        String name = getName();
        String[] ingredients = getIngredients();
        mealDao.add(new Meal(category, name, ingredients));
        System.out.println("The meal has been added!");
    }

    private Category getCategory() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        while (true) {
            try {
                return Category.valueOf(in.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
    }

    private String getName() {
        System.out.println("Input the meal's name:");
        while (true) {
            try {
                return trimAndValidate(in.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    private String[] getIngredients() {
        System.out.println("Input the ingredients:");
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
        List<Meal> meals = mealDao.findAll();
        if (meals.isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
        } else {
            meals.forEach(meal -> System.out.printf("%n%s%n", meal));
            System.out.println();
        }
    }
}
