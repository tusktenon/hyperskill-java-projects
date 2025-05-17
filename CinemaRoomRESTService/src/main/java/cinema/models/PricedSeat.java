package cinema.models;

import java.util.function.IntBinaryOperator;

public record PricedSeat(int row, int column, int price) {

    public PricedSeat(int row, int column, IntBinaryOperator pricer) {
        this(row, column, pricer.applyAsInt(row, column));
    }
}
