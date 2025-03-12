package tictactoe;

class Game {
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

  private final Player xPlayer, oPlayer;
  private final Table table;
  private State state = State.ACTIVE;
  private boolean isXsMove = true;

  Game(Player xPlayer, Player oPlayer, Table table) {
    this.xPlayer = xPlayer;
    this.oPlayer = oPlayer;
    this.table = table;
  }

  void play() {
    while (state == State.ACTIVE) {
      System.out.println(table);
      if (isXsMove) xPlayer.move();
      else oPlayer.move();
      isXsMove = !isXsMove;
      updateState();
    }
    System.out.println(table);
    System.out.println(state);
  }

  private void updateState() {
    state =
        table.hasThreeInARow(Square.X)
            ? State.X_WINS
            : table.hasThreeInARow(Square.O)
                ? State.O_WINS
                : table.isFull() ? State.DRAW : State.ACTIVE;
  }
}
