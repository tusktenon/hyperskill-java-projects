package mealplanner;

import java.util.Scanner;

import static mealplanner.Meal.Category;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
            Category category = Category.valueOf(in.nextLine());

            System.out.println("Input the meal's name:");
            String name = in.nextLine();

            System.out.println("Input the ingredients:");
            String[] ingredients = in.nextLine().split(",");

            Meal meal = new Meal(category, name, ingredients);

            System.out.println();
            System.out.println(meal);
            System.out.println("The meal has been added!");
        }
    }
}
