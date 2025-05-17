package cinema.models;

import java.util.function.IntBinaryOperator;

public record Ticket(int row, int column, int price) {

    public Ticket(int row, int column, IntBinaryOperator pricer) {
        this(row, column, pricer.applyAsInt(row, column));
    }
}
