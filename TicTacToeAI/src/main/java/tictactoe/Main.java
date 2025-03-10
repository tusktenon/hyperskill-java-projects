package tictactoe;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      Game game = new Game(in);
      game.play();
    }
  }
}
