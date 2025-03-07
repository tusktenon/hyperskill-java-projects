package machine;

import java.util.Scanner;

record Coffee(int water, int milk, int beans, int price) {}

public class CoffeeMachine {
  private static final Coffee ESPRESSO = new Coffee(250, 0, 16, 4);
  private static final Coffee LATTE = new Coffee(350, 75, 20, 7);
  private static final Coffee CAPPUCCINO = new Coffee(200, 100, 12, 6);

  private int water, milk, beans, cups, money;
  private Scanner in;

  CoffeeMachine(int water, int milk, int beans, int cups, int money, Scanner in) {
    this.water = water;
    this.milk = milk;
    this.beans = beans;
    this.cups = cups;
    this.money = money;
    this.in = in;
  }

  void run() {
    System.out.println(mainDisplay());
    System.out.print("\nWrite action (buy, fill, take):\n> ");
    switch (in.nextLine()) {
      case "buy" -> sell();
      case "fill" -> refill();
      case "take" -> emptyMoney();
      default -> System.out.println("Invalid selection");
    }
    System.out.println("\n" + mainDisplay());
  }

  private String mainDisplay() {
    return "The coffee machine has:\n"
        + "%d ml of water\n%d ml of milk\n%d g of coffee beans\n%d disposable cups\n$%d of money"
            .formatted(water, milk, beans, cups, money);
  }

  private void sell() {
    System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:\n> ");
    Coffee selection =
        switch (in.nextLine()) {
          case "1" -> ESPRESSO;
          case "2" -> LATTE;
          case "3" -> CAPPUCCINO;
          default -> null;
        };
    if (selection != null) {
      water -= selection.water();
      milk -= selection.milk();
      beans -= selection.beans();
      cups--;
      money += selection.price();
    } else {
      System.out.println("Invalid selection");
    }
  }

  private void refill() {
    System.out.print("Write how many ml of water you want to add:\n> ");
    water += in.nextInt();
    System.out.print("Write how many ml of milk you want to add:\n> ");
    milk += in.nextInt();
    System.out.print("Write how many grams of coffee beans you want to add:\n> ");
    beans += in.nextInt();
    System.out.print("Write how many disposable cups you want to add:\n> ");
    cups += in.nextInt();
  }

  private void emptyMoney() {
    int withdrawn = money;
    money = 0;
    System.out.println("I gave you $" + withdrawn);
  }

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550, in);
      machine.run();
    }
  }
}
