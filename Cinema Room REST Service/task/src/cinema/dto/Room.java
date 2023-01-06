package cinema.dto;

import java.util.ArrayList;

public class Room {
    int totalRows;
    int totalColumns;
    ArrayList<Seat> availableSeats;

    public Room() {
    }

    public Room(int total_rows, int total_columns, ArrayList<Seat> available_seats) {
        this.totalRows = total_rows;
        this.totalColumns = total_columns;
        this.availableSeats = available_seats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public ArrayList<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(ArrayList<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }
}