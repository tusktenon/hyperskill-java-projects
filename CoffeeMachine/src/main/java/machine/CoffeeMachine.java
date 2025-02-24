package machine;

import java.util.List;

public class CoffeeMachine {
  public static final List<String> STEPS =
      List.of(
          "Starting to make a coffee",
          "Grinding coffee beans",
          "Boiling water",
          "Mixing boiled water with crushed coffee beans",
          "Pouring coffee into the cup",
          "Pouring some milk into the cup",
          "Coffee is ready!");

  public static void main(String[] args) {
    STEPS.forEach(System.out::println);
  }
}
