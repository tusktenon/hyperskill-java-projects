package tictactoe;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      Table table = new Table();
      Player user = new User(table, in);
      Player easy = new EasyAI(table);
      Game game = new Game(user, easy, table);
      game.play();
    }
  }
}
