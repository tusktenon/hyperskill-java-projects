package tictactoe;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/* The class name `Main` is required by the exercise; otherwise, I'd prefer `Game`. */
public class Main {
  private char[] squares;

  Main(String state) {
    if (!Pattern.matches("[XO_]{9}", state)) {
      throw new IllegalArgumentException("Invalid game-state string");
    }
    squares = state.toCharArray();
  }

  String grid() {
    return "---------\n"
        + "| %c %c %c |\n".formatted(squares[0], squares[1], squares[2])
        + "| %c %c %c |\n".formatted(squares[3], squares[4], squares[5])
        + "| %c %c %c |\n".formatted(squares[6], squares[7], squares[8])
        + "---------\n";
  }

  String state() {
    if (!isValid()) return "Impossible";
    if (xWins()) return "X wins";
    if (oWins()) return "O wins";
    if (isFull()) return "Draw";
    return "Game not finished";
  }

  private void getNextMove(Scanner in) {
    while (true) {
      System.out.print("> ");
      try {
        int row = in.nextInt();
        int col = in.nextInt();
        if (row < 1 || row > 3 || col < 1 || col > 3) {
          System.out.println("Coordinates should be from 1 to 3!");
        } else if (squares[(row - 1) * 3 + (col - 1)] != '_') {
          System.out.println("This cell is occupied! Choose another one!");
        } else {
          squares[(row - 1) * 3 + (col - 1)] = 'X';
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
    return count('_') == 0;
  }

  private boolean isValid() {
    return !(xWins() && oWins()) && Math.abs(count('X') - count('O')) < 2;
  }

  private boolean hasThreeInARow(char c) {
    int[][] threeInARowIndexes = {
      {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
    };
    for (int[] triplet : threeInARowIndexes) {
      if (c == squares[triplet[0]] && c == squares[triplet[1]] && c == squares[triplet[2]]) {
        return true;
      }
    }
    return false;
  }

  private int count(char c) {
    int result = 0;
    for (char cell : squares) {
      if (cell == c) result++;
    }
    return result;
  }

  public static void main(String[] args) {
    System.out.print("> ");
    try (Scanner in = new Scanner(System.in)) {
      Main game = new Main(in.nextLine());
      System.out.println(game.grid());
      game.getNextMove(in);
      System.out.println(game.grid());
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }
}
