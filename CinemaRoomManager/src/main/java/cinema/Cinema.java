package cinema;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Cinema {
  private final int rows, seatsPerRow;
  private int reservedRow, reservedSeat;

  Cinema(int rows, int seatsPerRow) {
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
  }

  void reserve(int row, int seat) {
    reservedRow = row;
    reservedSeat = seat;
  }

  String seatingChart() {
    StringBuilder chart = new StringBuilder(" ");
    IntStream.rangeClosed(1, seatsPerRow).forEach(i -> chart.append(" " + i));
    IntStream.rangeClosed(1, rows)
        .forEach(
            i -> {
              chart.append("\n" + i);
              if (i == reservedRow) {
                chart.append(
                    " S".repeat(reservedSeat - 1) + " B" + " S".repeat(seatsPerRow - reservedSeat));
              } else {
                chart.append(" S".repeat(seatsPerRow));
              }
            });
    return chart.toString();
  }

  int ticketPrice() {
    return rows * seatsPerRow > 60 && reservedRow > rows / 2 ? 8 : 10;
  }

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.print("Enter the number of rows:\n> ");
      int rows = in.nextInt();

      System.out.print("Enter the number of seats in each row:\n> ");
      int seatsPerRow = in.nextInt();

      Cinema cinema = new Cinema(rows, seatsPerRow);
      System.out.println("\nCinema:");
      System.out.println(cinema.seatingChart());

      System.out.print("\nEnter a row number:\n> ");
      int row = in.nextInt();

      System.out.print("Enter a seat number in that row:\n> ");
      int seat = in.nextInt();

      cinema.reserve(row, seat);
      System.out.println("\nTicket price: $" + cinema.ticketPrice());
      System.out.println("\nCinema:");
      System.out.println(cinema.seatingChart());
    }
  }
}
