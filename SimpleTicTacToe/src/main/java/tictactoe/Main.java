package tictactoe;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* The class name `Main` is required by the exercise; otherwise, I'd prefer `Game`. */
public class Main {
  private char[] squares = new char[9];
  private boolean isXsMove = true;

  Main() {
    Arrays.fill(squares, ' ');
  }

  void play() {
    try (Scanner in = new Scanner(System.in)) {
      while (true) {
        System.out.println(grid());
        if (xWins()) {
          System.out.println("X wins");
          return;
        }
        if (oWins()) {
          System.out.println("O wins");
          return;
        }
        if (isFull()) {
          System.out.println("Draw");
          return;
        }
        getNextMove(in);
        isXsMove = !isXsMove;
      }
    }
  }

  String grid() {
    return "---------\n"
        + "| %c %c %c |\n".formatted(squares[0], squares[1], squares[2])
        + "| %c %c %c |\n".formatted(squares[3], squares[4], squares[5])
        + "| %c %c %c |\n".formatted(squares[6], squares[7], squares[8])
        + "---------\n";
  }

  private void getNextMove(Scanner in) {
    while (true) {
      System.out.print("> ");
      try {
        int row = in.nextInt();
        int col = in.nextInt();
        if (row < 1 || row > 3 || col < 1 || col > 3) {
          System.out.println("Coordinates should be from 1 to 3!");
        } else if (squares[(row - 1) * 3 + (col - 1)] != ' ') {
          System.out.println("This cell is occupied! Choose another one!");
        } else {
          char mark = isXsMove ? 'X' : 'O';
          squares[(row - 1) * 3 + (col - 1)] = mark;
          break;
        }
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear current input before next loop
        System.out.println("You should enter numbers!");
      }
    }
  }

  private boolean xWins() {
    return hasThreeInARow('X');
  }

  private boolean oWins() {
    return hasThreeInARow('O');
  }

  private boolean isFull() {
    for (char cell : squares) {
      if (cell == ' ') return false;
    }
    return true;
  }

  private boolean hasThreeInARow(char c) {
    int[][] threeInARowIndexes = {
      {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
    };
    for (int[] i : threeInARowIndexes) {
      if (squares[i[0]] == c && squares[i[1]] == c && squares[i[2]] == c) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    Main game = new Main();
    game.play();
  }
}
