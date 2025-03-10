package tictactoe;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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

  enum Level {
    EASY;

    @Override
    public String toString() {
      return this.name().toLowerCase();
    }
  }

  private final Level level = Level.EASY;
  private final Square[] squares;
  private State state = State.ACTIVE;
  private boolean isXsMove = true;
  private final Scanner in;

  // The `RandomGenerator.getDefault()` factory method is preferred in modern Java,
  // but the Hyperskill tests do not support it.
  private final RandomGenerator rng = new Random();

  Game(Scanner in) {
    this.in = in;
    squares = new Square[9];
    Arrays.fill(squares, Square.EMPTY);
  }

  /* For use in the `withInitialState` factory method. */
  private Game(Scanner in, Square[] squares) {
    this.squares = squares;
    this.in = in;
  }

  /* Not used in Stage 2. */
  static Game withInitialState(Scanner in) {
    System.out.print("Enter the cells: > ");
    String initialState = in.nextLine();

    if (!Pattern.matches("[XO_]{9}", initialState)) {
      throw new IllegalArgumentException("Invalid initial game state");
    }

    Square[] squares = new Square[9];
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

    boolean isXsMove =
        switch (xoDiff) {
          case 0 -> true;
          case 1 -> false;
          default -> throw new IllegalArgumentException("Invalid initial game state");
        };

    Game game = new Game(in, squares);
    game.updateState();
    game.isXsMove = isXsMove;
    return game;
  }

  void play() {
    while (state == State.ACTIVE) {
      System.out.println(table());
      if (isXsMove) movePlayer();
      else moveAI();
      isXsMove = !isXsMove;
      updateState();
    }
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

  private void movePlayer() {
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

  private void moveAI() {
    int[] available = IntStream.range(0, 9).filter(i -> squares[i] == Square.EMPTY).toArray();
    squares[available[rng.nextInt(available.length)]] = Square.O;
    System.out.printf("Making move level \"%s\"\n", level);
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
