package cinema;

import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

interface TicketPricer {
  int ticketPrice(int row, int seat);

  int maxIncome();
}

class FixedTicketPricer implements TicketPricer {
  static final int PRICE = 10;
  final int seats;

  FixedTicketPricer(int seats) {
    this.seats = seats;
  }

  @Override
  public int ticketPrice(int row, int seat) {
    return PRICE;
  }

  @Override
  public int maxIncome() {
    return PRICE * seats;
  }
}

class RowBasedTicketPricer implements TicketPricer {
  static final int FRONT_PRICE = 10;
  static final int BACK_PRICE = 8;
  final int rows, seatsPerRow;

  RowBasedTicketPricer(int rows, int seatsPerRow) {
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
  }

  @Override
  public int ticketPrice(int row, int seat) {
    return row <= rows / 2 ? FRONT_PRICE : BACK_PRICE;
  }

  @Override
  public int maxIncome() {
    return (FRONT_PRICE * (rows / 2) + BACK_PRICE * (rows - rows / 2)) * seatsPerRow;
  }
}

public class Cinema {
  private static final String MENU =
      """

      1. Show the seats
      2. Buy a ticket
      3. Statistics
      0. Exit
      > \
      """;

  private final int rows, seatsPerRow;
  private final BitSet reserved;
  private final TicketPricer pricer;
  private int ticketsSold;
  private int income;
  private final int maxIncome;
  private final Scanner in;

  Cinema(int rows, int seatsPerRow, Scanner in) {
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
    this.in = in;
    int totalSeats = rows * seatsPerRow;
    reserved = new BitSet(totalSeats);
    pricer =
        totalSeats > 60
            ? new RowBasedTicketPricer(rows, seatsPerRow)
            : new FixedTicketPricer(totalSeats);
    maxIncome = pricer.maxIncome();
  }

  static Cinema setup(Scanner in) {
    System.out.print("Enter the number of rows:\n> ");
    int rows = in.nextInt();

    System.out.print("Enter the number of seats in each row:\n> ");
    int seatsPerRow = in.nextInt();

    return new Cinema(rows, seatsPerRow, in);
  }

  void run() {
    while (true) {
      System.out.print(MENU);
      try {
        int selection = in.nextInt();
        switch (selection) {
          case 0 -> {
            return;
          }
          case 1 -> System.out.println(seatingChart());
          case 2 -> sellTicket();
          case 3 -> System.out.println(statistics());
          default -> System.out.println("Invalid selection");
        }
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear input buffer
        System.out.println("Invalid selection");
      }
    }
  }

  private void sellTicket() {
    int row = 0, seat = 0;
    boolean validInput = false;
    do {
      try {
        System.out.print("\nEnter a row number:\n> ");
        row = in.nextInt();
        System.out.print("Enter a seat number in that row:\n> ");
        seat = in.nextInt();

        if (row < 1 || row > rows || seat < 1 || seat > seatsPerRow)
          System.out.println("\nWrong input!");
        else if (isReserved(row, seat))
          System.out.println("\nThat ticket has already been purchased!");
        else validInput = true;
      } catch (NoSuchElementException e) {
        in.nextLine(); // clear input buffer
        System.out.println("\nWrong input!");
      }
    } while (!validInput);
    int price = reserve(row, seat);
    System.out.println("\nTicket price: $" + price);
  }

  private int reserve(int row, int seat) {
    reserved.set((row - 1) * seatsPerRow + (seat - 1));
    ticketsSold++;
    int price = pricer.ticketPrice(row, seat);
    income += price;
    return price;
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

  String statistics() {
    return "\nNumber of purchased tickets: %d\nPercentage: %.2f%%\nCurrent income: $%d\nTotal income: $%d"
        .formatted(ticketsSold, 100.0 * ticketsSold / (rows * seatsPerRow), income, maxIncome);
  }

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      Cinema cinema = Cinema.setup(in);
      cinema.run();
    }
  }
}
