package cinema;

import cinema.models.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntBinaryOperator;

public class SeatingService {

    private final int rows;
    private final int columns;
    private final IntBinaryOperator pricer;
    private final SeatingPlan seatingPlan;
    private final Set<Seat> purchased = ConcurrentHashMap.newKeySet();
    private final Map<UUID, Ticket> sales = new ConcurrentHashMap<>();

    public SeatingService(int rows, int columns, IntBinaryOperator pricer) {
        this.rows = rows;
        this.columns = columns;
        this.pricer = pricer;
        this.seatingPlan = new SeatingPlan(rows, columns, pricer);
    }

    public SeatingPlan seatingPlan() {
        return seatingPlan;
    }

    public TicketPurchase purchase(Seat seat) {
        int row = seat.row();
        int column = seat.column();
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new IllegalArgumentException("The number of a row or a column is out of bounds!");
        }
        if (purchased.add(seat)) {
            UUID token = UUID.randomUUID();
            Ticket ticket = new Ticket(row, column, pricer);
            sales.put(token, ticket);
            return new TicketPurchase(token, ticket);
        } else {
            throw new IllegalArgumentException("The ticket has been already purchased!");
        }
    }

    public TicketRefund refund(PurchaseToken purchase) {
        Ticket ticket = sales.remove(purchase.token());
        if (ticket != null) {
            purchased.remove(new Seat(ticket.row(), ticket.column()));
            return new TicketRefund(ticket);
        } else {
            throw new IllegalArgumentException("Wrong token!");
        }
    }

    public Statistics statistics() {
        int income = sales.values().stream().mapToInt(Ticket::price).sum();
        int capacity = seatingPlan.seats().size();
        int reserved = purchased.size();
        return new Statistics(income, capacity - reserved, reserved);
    }
}
