package cinema.models;

import java.util.UUID;

public record TicketPurchase(UUID token, Ticket ticket) {}
