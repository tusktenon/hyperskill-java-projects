package cinema;

import java.util.stream.IntStream;

public class Cinema {
  public static final int ROWS = 7;
  public static final int SEATS_PER_ROW = 8;

  public static void main(String[] args) {
    StringBuilder headerRowBuilder = new StringBuilder();
    IntStream.rangeClosed(1, SEATS_PER_ROW).forEach(i -> headerRowBuilder.append(" " + i));
    String headerRow = headerRowBuilder.toString();

    StringBuilder rowBuilder = new StringBuilder();
    IntStream.rangeClosed(1, SEATS_PER_ROW).forEach(i -> rowBuilder.append(" S"));
    String row = rowBuilder.toString();

    System.out.println("Cinema:");
    System.out.println(" " + headerRow);
    IntStream.rangeClosed(1, ROWS).forEach(i -> System.out.println(i + row));
  }
}
