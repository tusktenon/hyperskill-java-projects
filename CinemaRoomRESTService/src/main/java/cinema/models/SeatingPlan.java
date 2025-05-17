package cinema.models;

import java.util.ArrayList;
import java.util.List;

public record SeatingPlan(int rows, int columns, List<Seat> seats) {

    public SeatingPlan(int rows, int columns) {
        this(rows, columns, new ArrayList<>(rows * columns));
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++)
                seats.add(new Seat(row, column));
        }
    }
}
