package cinema;

import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Cinema {
  private static final String menu =
      """

      1. Show the seats
      2. Buy a ticket
      0. Exit
      > \
      """;

  private final int rows, seatsPerRow;
  private final BitSet reserved;

  Cinema(int rows, int seatsPerRow) {
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
    reserved = new BitSet(rows * seatsPerRow);
  }

  void run(Scanner in) {
    while (true) {
      System.out.print(menu);
      try {
        int selection = in.nextInt();
        switch (selection) {
          case 0 -> {
            return;
          }
          case 1 -> System.out.println(seatingChart());
          case 2 -> {
            System.out.print("\nEnter a row number:\n> ");
            int row = in.nextInt();

            System.out.print("Enter a seat number in that row:\n> ");
            int seat = in.nextInt();

            reserve(row, seat);
            System.out.println("\nTicket price: $" + ticketPrice(row));
          }
          default -> System.out.println("Invalid selection");
        }
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear input buffer
        System.out.println("Invalid selection");
      }
    }
  }

  private void reserve(int row, int seat) {
    reserved.set((row - 1) * seatsPerRow + (seat - 1));
  }

  private boolean isReserved(int row, int seat) {
    return reserved.get((row - 1) * seatsPerRow + (seat - 1));
  }

  String seatingChart() {
    StringBuilder chart = new StringBuilder("\nCinema:\n ");
    IntStream.rangeClosed(1, seatsPerRow).forEach(i -> chart.append(" " + i));
    IntStream.rangeClosed(1, rows)
        .forEach(
            i -> {
              chart.append("\n" + i);
              IntStream.rangeClosed(1, seatsPerRow)
                  .forEach(j -> chart.append(isReserved(i, j) ? " B" : " S"));
            });
    return chart.toString();
  }

  int ticketPrice(int row) {
    return rows * seatsPerRow > 60 && row > rows / 2 ? 8 : 10;
  }

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.print("Enter the number of rows:\n> ");
      int rows = in.nextInt();

      System.out.print("Enter the number of seats in each row:\n> ");
      int seatsPerRow = in.nextInt();

      Cinema cinema = new Cinema(rows, seatsPerRow);
      cinema.run(in);
    }
  }
}
