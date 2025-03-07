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
    while (true) {
      System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
      switch (in.nextLine()) {
        case "buy" -> sell();
        case "fill" -> refill();
        case "take" -> emptyMoney();
        case "remaining" -> printStatus();
        case "exit" -> {
          return;
        }
        default -> System.out.println("Invalid selection");
      }
    }
  }

  private void printStatus() {
    System.out.println("\nThe coffee machine has:");
    System.out.println(
        "%d ml of water\n%d ml of milk\n%d g of coffee beans\n%d disposable cups\n$%d of money"
            .formatted(water, milk, beans, cups, money));
  }

  private void sell() {
    System.out.print(
        "\nWhat do you want to buy? "
            + "1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n> ");
    Coffee selection = null;
    switch (in.nextLine()) {
      case "1" -> selection = ESPRESSO;
      case "2" -> selection = LATTE;
      case "3" -> selection = CAPPUCCINO;
      case "back" -> {
        return;
      }
      default -> {
        System.out.println("Invalid selection");
        return;
      }
    }
    if (canMake(selection)) {
      System.out.println("I have enough resources, making you a coffee!");
      makeAndSell(selection);
    }
  }

  private boolean canMake(Coffee coffee) {
    if (water < coffee.water()) {
      System.out.println("Sorry, not enough water!");
      return false;
    }
    if (milk < coffee.milk()) {
      System.out.println("Sorry, not enough milk!");
      return false;
    }
    if (beans < coffee.beans()) {
      System.out.println("Sorry, not enough beans!");
      return false;
    }
    if (cups < 1) {
      System.out.println("Sorry, out of cups!");
      return false;
    }
    return true;
  }

  private void makeAndSell(Coffee coffee) {
    water -= coffee.water();
    milk -= coffee.milk();
    beans -= coffee.beans();
    cups--;
    money += coffee.price();
  }

  private void refill() {
    System.out.print("\nWrite how many ml of water you want to add:\n> ");
    water += in.nextInt();
    System.out.print("Write how many ml of milk you want to add:\n> ");
    milk += in.nextInt();
    System.out.print("Write how many grams of coffee beans you want to add:\n> ");
    beans += in.nextInt();
    System.out.print("Write how many disposable cups you want to add:\n> ");
    cups += in.nextInt();
    in.nextLine(); // clear buffer
  }

  private void emptyMoney() {
    int withdrawn = money;
    money = 0;
    System.out.println("\nI gave you $" + withdrawn);
  }

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550, in);
      machine.run();
    }
  }
}
