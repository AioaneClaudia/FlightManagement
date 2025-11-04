package com.example.flightmanagement.model;

//modificare enum
public class Luggage {
    private String id;
    private String ticketId;
    private String status; // CheckedIn, Loaded, Delivered

    public Luggage(String id, String ticketId, String status) {
        this.id = id;
        this.ticketId = ticketId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {

        this.ticketId = ticketId;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    @Override
    public String toString() {
        return "Luggage{" +
                "id='" + id + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
