package tictactoe;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      Menu menu = new Menu(in);
      menu.run();
    }
  }
}
