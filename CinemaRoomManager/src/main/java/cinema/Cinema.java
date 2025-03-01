package cinema;

import java.util.Scanner;

public class Cinema {

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.println("Enter the number of rows:");
      System.out.print("> ");
      int rows = in.nextInt();

      System.out.println("Enter the number of seats in each row:");
      System.out.print("> ");
      int seats = in.nextInt();

      int profit =
          rows * seats > 60 ? ((rows / 2) * 10 + (rows - rows / 2) * 8) * seats : rows * seats * 10;
      System.out.println("Total income:");
      System.out.println("$" + profit);
    }
  }
}
