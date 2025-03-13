package tictactoe;

import java.util.Random;
import java.util.random.RandomGenerator;

class EasyAI implements Player {
  private final Square playingAs;
  private final Table table;
  private final RandomGenerator rng = new Random();

  EasyAI(Square playingAs, Table table) {
    this.playingAs = playingAs;
    this.table = table;
  }

  @Override
  public void move() {
    int[] available = table.availableCells();
    table.set(available[rng.nextInt(available.length)], playingAs);
    System.out.println("Making move level \"easy\"");
  }
}
