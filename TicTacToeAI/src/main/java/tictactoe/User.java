package tictactoe;

import java.util.NoSuchElementException;
import java.util.Scanner;

class User implements Player {
  private final Table table;
  private final Scanner in;

  User(Table table, Scanner in) {
    this.table = table;
    this.in = in;
  }

  @Override
  public void move() {
    while (true) {
      System.out.print("Enter the coordinates: > ");
      try {
        int row = in.nextInt();
        int col = in.nextInt();
        if (row < 1 || row > 3 || col < 1 || col > 3)
          System.out.println("Coordinates should be from 1 to 3!");
        else if (table.set(row, col, Square.X)) break;
        else System.out.println("This cell is occupied! Choose another one!");
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear current input before next loop
        System.out.println("You should enter numbers!");
      }
    }
  }
}
