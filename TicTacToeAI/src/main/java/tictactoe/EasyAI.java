package tictactoe;

import java.util.Random;
import java.util.random.RandomGenerator;

class EasyAI implements Player {
  private final Table table;
  private final RandomGenerator rng = new Random();

  EasyAI(Table table) {
    this.table = table;
  }

  @Override
  public void move() {
    int[] available = table.availableCells();
    table.set(available[rng.nextInt(available.length)], Square.O);
    System.out.println("Making move level \"easy\"");
  }
}
