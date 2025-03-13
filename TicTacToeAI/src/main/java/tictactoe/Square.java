package tictactoe;

enum Square {
  X,
  O,
  EMPTY;

  @Override
  public String toString() {
    return switch (this) {
      case X -> "X";
      case O -> "O";
      case EMPTY -> " ";
    };
  }
}
