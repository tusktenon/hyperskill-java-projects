package machine;

import java.util.Arrays;

public class CoffeeMachine {
  public static final String[] STEPS = {
    "Starting to make a coffee",
    "Grinding coffee beans",
    "Boiling water",
    "Mixing boiled water with crushed coffee beans",
    "Pouring coffee into the cup",
    "Pouring some milk into the cup",
    "Coffee is ready!"
  };

  public static void main(String[] args) {
    Arrays.asList(STEPS).forEach(System.out::println);
  }
}
