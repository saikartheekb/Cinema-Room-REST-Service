package cinema.dto;

import java.util.UUID;


public class PurchasedTicket {
    UUID token;
    Seat ticket;

    public PurchasedTicket() {
    }

    public PurchasedTicket(UUID token, Seat seat) {
        this.token = token;
        this.ticket = seat;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }
}