package machine;

import java.util.Scanner;
import java.util.stream.IntStream;

public class CoffeeMachine {
  private static final int WATER_PER_CUP = 200;
  private static final int MILK_PER_CUP = 50;
  private static final int BEANS_PER_CUP = 15;

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.print("Write how many ml of water the coffee machine has:\n> ");
      int water = in.nextInt();
      System.out.print("Write how many ml of milk the coffee machine has:\n> ");
      int milk = in.nextInt();
      System.out.print("Write how many grams of coffee beans the coffee machine has:\n> ");
      int beans = in.nextInt();
      System.out.print("Write how many cups of coffee you will need:\n> ");
      int cupsNeeded = in.nextInt();

      int cupsPossible =
          IntStream.of(water / WATER_PER_CUP, milk / MILK_PER_CUP, beans / BEANS_PER_CUP)
              .min()
              .getAsInt();

      if (cupsPossible > cupsNeeded) {
        System.out.printf(
            "Yes, I can make that amount of coffee (and even %d more than that)\n",
            cupsPossible - cupsNeeded);
      } else if (cupsPossible == cupsNeeded) {
        System.out.println("Yes, I can make that amount of coffee");
      } else {
        System.out.printf("No, I can make only %d cup(s) of coffee", cupsPossible);
      }
    }
  }
}
