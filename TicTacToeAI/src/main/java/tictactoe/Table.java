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
    return "---------\n"
        + "| %s %s %s |\n".formatted(squares[0], squares[1], squares[2])
        + "| %s %s %s |\n".formatted(squares[3], squares[4], squares[5])
        + "| %s %s %s |\n".formatted(squares[6], squares[7], squares[8])
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
    return Arrays.stream(threeInARowIndexes)
        .anyMatch(i -> squares[i[0]] == s && squares[i[1]] == s && squares[i[2]] == s);
  }

  boolean isFull() {
    return !Arrays.stream(squares).anyMatch(s -> s == Square.EMPTY);
  }
}
