package tictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

class Table {
  private final Square[] squares;

  Table() {
    squares = new Square[9];
    Arrays.fill(squares, Square.EMPTY);
  }

  @Override
  public String toString() {
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

  void set(int index, Square square) {
    squares[index] = square;
  }

  boolean set(int row, int col, Square square) {
    int i = (row - 1) * 3 + (col - 1);
    if (squares[i] != Square.EMPTY) return false;
    squares[i] = square;
    return true;
  }

  int[] availableCells() {
    return IntStream.range(0, 9).filter(i -> squares[i] == Square.EMPTY).toArray();
  }

  boolean hasThreeInARow(Square s) {
    int[][] threeInARowIndexes = {
      {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
    };
    for (int[] i : threeInARowIndexes) {
      if (squares[i[0]] == s && squares[i[1]] == s && squares[i[2]] == s) return true;
    }
    return false;
  }

  boolean isFull() {
    for (Square cell : squares) {
      if (cell == Square.EMPTY) return false;
    }
    return true;
  }
}
