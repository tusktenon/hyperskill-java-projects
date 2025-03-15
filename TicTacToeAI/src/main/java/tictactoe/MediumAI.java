package tictactoe;

import java.util.Random;
import java.util.random.RandomGenerator;

class MediumAI implements Player {
  private final Square playingAs;
  private final Table table;
  private final RandomGenerator rng = new Random();

  MediumAI(Square playingAs, Table table) {
    this.playingAs = playingAs;
    this.table = table;
  }

  @Override
  public void move() {
    System.out.println("Making move level \"medium\"");
    int[] available = table.availableCells();

    // Win game if possible
    for (int i : available) {
      table.set(i, playingAs);
      if (table.hasThreeInARow(playingAs)) return;
      else table.set(i, Square.EMPTY);
    }

    // Block opponent from winning
    Square opponentPlayingAs = playingAs == Square.X ? Square.O : Square.X;
    for (int i : available) {
      table.set(i, opponentPlayingAs);
      if (table.hasThreeInARow(opponentPlayingAs)) {
        table.set(i, playingAs);
        return;
      } else {
        table.set(i, Square.EMPTY);
      }
    }

    // Make a random move
    table.set(available[rng.nextInt(available.length)], playingAs);
  }
}
