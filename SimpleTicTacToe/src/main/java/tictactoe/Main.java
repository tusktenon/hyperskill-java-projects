package tictactoe;

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

  public static void main(String[] args) {
    System.out.print("> ");
    try (Scanner in = new Scanner(System.in)) {
      Main game = new Main(in.nextLine());
      System.out.println(game.grid());
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
  }
}
