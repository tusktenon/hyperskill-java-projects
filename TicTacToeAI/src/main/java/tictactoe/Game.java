package tictactoe;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

class Game {
  enum Square {
    X,
    O,
    EMPTY
  }

  enum State {
    ACTIVE,
    X_WINS,
    O_WINS,
    DRAW;

    @Override
    public String toString() {
      return switch (this) {
        case ACTIVE -> "Game not finished";
        case X_WINS -> "X wins";
        case O_WINS -> "O wins";
        case DRAW -> "Draw";
      };
    }
  }

  private final Square[] squares = new Square[9];
  private State state;
  private boolean isXsMove;
  private final Scanner in;

  Game(Scanner in) {
    this.in = in;
    System.out.print("Enter the cells: > ");
    String initialState = in.nextLine();

    if (!Pattern.matches("[XO_]{9}", initialState)) {
      throw new IllegalArgumentException("Invalid initial game state");
    }

    int xoDiff = 0;
    for (int i = 0; i < 9; i++) {
      switch (initialState.charAt(i)) {
        case 'X' -> {
          squares[i] = Square.X;
          xoDiff++;
        }
        case 'O' -> {
          squares[i] = Square.O;
          xoDiff--;
        }
        default -> squares[i] = Square.EMPTY;
      }
    }

    isXsMove =
        switch (xoDiff) {
          case 0 -> true;
          case 1 -> false;
          default -> throw new IllegalArgumentException("Invalid initial game state");
        };

    updateState();
  }

  void play() {
    System.out.println(table());
    getNextMove();
    isXsMove = !isXsMove;
    updateState();
    System.out.println(table());
    System.out.println(state);
  }

  String table() {
    char[] cells = new char[9];
    for (int i = 0; i < 9; i++) {
      cells[i] =
          switch (squares[i]) {
            case X -> 'X';
            case O -> 'O';
            case EMPTY -> ' ';
          };
    }

    return "---------\n"
        + "| %c %c %c |\n".formatted(cells[0], cells[1], cells[2])
        + "| %c %c %c |\n".formatted(cells[3], cells[4], cells[5])
        + "| %c %c %c |\n".formatted(cells[6], cells[7], cells[8])
        + "---------";
  }

  private void getNextMove() {
    while (true) {
      System.out.print("Enter the coordinates: > ");
      try {
        int row = in.nextInt();
        int col = in.nextInt();
        if (row < 1 || row > 3 || col < 1 || col > 3) {
          System.out.println("Coordinates should be from 1 to 3!");
        } else if (squares[(row - 1) * 3 + (col - 1)] != Square.EMPTY) {
          System.out.println("This cell is occupied! Choose another one!");
        } else {
          Square entry = isXsMove ? Square.X : Square.O;
          squares[(row - 1) * 3 + (col - 1)] = entry;
          break;
        }
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear current input before next loop
        System.out.println("You should enter numbers!");
      }
    }
  }

  private void updateState() {
    state =
        hasThreeInARow(Square.X)
            ? State.X_WINS
            : hasThreeInARow(Square.O) ? State.O_WINS : isFull() ? State.DRAW : State.ACTIVE;
  }

  private boolean hasThreeInARow(Square s) {
    int[][] threeInARowIndexes = {
      {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
    };
    for (int[] i : threeInARowIndexes) {
      if (squares[i[0]] == s && squares[i[1]] == s && squares[i[2]] == s) return true;
    }
    return false;
  }

  private boolean isFull() {
    for (Square cell : squares) {
      if (cell == Square.EMPTY) return false;
    }
    return true;
  }
}
