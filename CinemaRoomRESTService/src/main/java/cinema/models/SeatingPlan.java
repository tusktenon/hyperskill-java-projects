package cinema.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public record SeatingPlan(int rows, int columns, List<Ticket> seats) {

    public SeatingPlan(int rows, int columns, IntBinaryOperator pricer) {
        this(rows, columns, new ArrayList<>(rows * columns));
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Ticket(row, column, pricer));
            }
        }
    }
}
