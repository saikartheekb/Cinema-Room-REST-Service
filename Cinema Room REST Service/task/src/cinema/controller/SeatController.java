package cinema.controller;

import cinema.dto.PurchasedTicket;
import cinema.dto.Room;
import cinema.dto.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SeatController {
    private final int totalRows = 9;
    private final int totalColumns = 9;
    private final ArrayList<Seat> availableseats = new ArrayList<>();
    private final ArrayList<PurchasedTicket> purchasedTickets = new ArrayList<>();
    private int totalPurschaseAmount;

    SeatController() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                Seat newSeat = new Seat(i, j);
                availableseats.add(newSeat);
                totalPurschaseAmount += newSeat.getPrice();
            }
        }
    }

    @GetMapping("/seats")
    public Room getAvailableseats() {
        Room room = new Room();
        room.setTotalRows(totalRows);
        room.setTotalColumns(totalColumns);

        room.setAvailableSeats(availableseats);
        return room;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat requestSeat) {
        // exception for invalid seat
        if ((requestSeat.getRow() <= 0 || requestSeat.getColumn() <= 0) ||
                (requestSeat.getRow() > totalRows || requestSeat.getColumn() > totalColumns)) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        for (Seat seat : availableseats) {
            if (seat.getColumn() == requestSeat.getColumn() && seat.getRow() == requestSeat.getRow()) {
                availableseats.remove(seat);
                UUID uuid = UUID.randomUUID();

                PurchasedTicket ticket = new PurchasedTicket(uuid, seat);
                purchasedTickets.add(ticket);

                return new ResponseEntity<>(ticket, HttpStatus.OK);
            }
        }

        // exception for already purchased seat request
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> returnTicketUUID) {
        for (PurchasedTicket ticket : purchasedTickets
        ) {
            System.out.println(ticket.getToken());
            System.out.println(returnTicketUUID.get("token"));
            if (ticket.getToken().compareTo(UUID.fromString(returnTicketUUID.get("token"))) == 0) {

                purchasedTickets.remove(ticket);
                availableseats.add(ticket.getTicket());
                return new ResponseEntity<>(Map.of("returned_ticket", ticket.getTicket()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("stats")
    public ResponseEntity<?> stats(@RequestParam(value = "password", required = false) String password){
        if(password == null || !password.equals("super_secret")){
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        int total_income = totalPurschaseAmount;
        for(Seat seat: availableseats){
            total_income -= seat.getPrice();
        }
        Map<String,Integer> statsMap = new HashMap<>();
        statsMap.put("current_income", total_income);
        statsMap.put("number_of_available_seats", availableseats.size());
        statsMap.put("number_of_purchased_tickets", purchasedTickets.size());
        return new ResponseEntity<>(statsMap, HttpStatus.OK);
    }

}