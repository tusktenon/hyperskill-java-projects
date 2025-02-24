package machine;

import java.util.Scanner;

public class CoffeeMachine {
  private static final int WATER_PER_CUP = 200;
  private static final int MILK_PER_CUP = 50;
  private static final int BEANS_PER_CUP = 15;

  public static void main(String[] args) {
    System.out.println("Write how many cups of coffee you will need:");
    System.out.print("> ");
    try (Scanner in = new Scanner(System.in)) {
      int cups = in.nextInt();
      System.out.println("For " + cups + " cups of coffee you will need:");
      System.out.println(WATER_PER_CUP * cups + " ml of water");
      System.out.println(MILK_PER_CUP * cups + " ml of milk");
      System.out.println(BEANS_PER_CUP * cups + " g of coffee beans");
    }
  }
}
