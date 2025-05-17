package cinema;

import cinema.models.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntBinaryOperator;

public class SeatingService {

    private final int rows;
    private final int columns;
    private final IntBinaryOperator pricer;
    private final SeatingPlan seatingPlan;
    private final Set<Seat> purchased = ConcurrentHashMap.newKeySet();

    public SeatingService(int rows, int columns, IntBinaryOperator pricer) {
        this.rows = rows;
        this.columns = columns;
        this.pricer = pricer;
        this.seatingPlan = new SeatingPlan(rows, columns, pricer);
    }

    public SeatingPlan seatingPlan() {
        return seatingPlan;
    }

    public Ticket purchase(Seat seat) {
        int row = seat.row();
        int column = seat.column();
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
        }
        if (purchased.add(seat)) {
            return new Ticket(row, column, pricer);
        } else {
            throw new IllegalArgumentException("The ticket has been already purchased!");
        }
    }
}
