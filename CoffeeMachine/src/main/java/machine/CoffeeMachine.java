package machine;

import java.util.Scanner;
import java.util.stream.IntStream;

public class CoffeeMachine {
  private static final int WATER_PER_CUP = 200;
  private static final int MILK_PER_CUP = 50;
  private static final int BEANS_PER_CUP = 15;

  private int water;
  private int milk;
  private int beans;

  public int cupsPossible() {
    return IntStream.of(water / WATER_PER_CUP, milk / MILK_PER_CUP, beans / BEANS_PER_CUP)
        .min()
        .getAsInt();
  }

  public String message(int needed) {
    int possible = cupsPossible();
    return possible > needed
        ? "Yes, I can make that amount of coffee (and even %d more than that)\n"
            .formatted(possible - needed)
        : possible == needed
            ? "Yes, I can make that amount of coffee"
            : "No, I can make only %d cup(s) of coffee".formatted(possible);
  }

  public static void main(String[] args) {
    CoffeeMachine machine = new CoffeeMachine();
    try (Scanner in = new Scanner(System.in)) {
      System.out.print("Write how many ml of water the coffee machine has:\n> ");
      machine.water = in.nextInt();
      System.out.print("Write how many ml of milk the coffee machine has:\n> ");
      machine.milk = in.nextInt();
      System.out.print("Write how many grams of coffee beans the coffee machine has:\n> ");
      machine.beans = in.nextInt();
      System.out.print("Write how many cups of coffee you will need:\n> ");
      int cupsNeeded = in.nextInt();
      System.out.println(machine.message(cupsNeeded));
    }
  }
}
