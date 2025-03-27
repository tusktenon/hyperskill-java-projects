package tictactoe;

import java.util.Scanner;

class Menu {
  private final Scanner in;

  Menu(Scanner in) {
    this.in = in;
  }

  void run() {
    while (true) {
      System.out.print("Input command: ");
      String[] command = in.nextLine().split("\\s+");
      if (command.length == 1 && "exit".equals(command[0])) return;
      if (command.length == 3 && "start".equals(command[0])) newGame(command[1], command[2]);
      else System.out.println("Bad parameters!");
    }
  }

  private void newGame(String xPlayerType, String oPlayerType) {
    try {
      Table table = new Table();
      Player xPlayer = newPlayer(xPlayerType, Square.X, table);
      Player oPlayer = newPlayer(oPlayerType, Square.O, table);
      Game game = new Game(xPlayer, oPlayer, table);
      game.play();
    } catch (IllegalArgumentException e) {
      System.out.println("Bad parameters!");
    }
  }

  private Player newPlayer(String playerType, Square playingAs, Table table) {
    return switch (playerType) {
      case "user" -> new User(playingAs, table, in);
      case "easy" -> new EasyAI(playingAs, table);
      case "medium" -> new MediumAI(playingAs, table);
      case "hard" -> new HardAI(playingAs, table);
      default -> throw new IllegalArgumentException("Invalid player type");
    };
  }
}
