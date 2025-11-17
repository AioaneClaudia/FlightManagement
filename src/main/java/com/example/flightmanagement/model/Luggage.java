package com.example.flightmanagement.model;

public class Luggage {
    private String id;
    private String ticketId;
    private LuggageStatus status;

    public Luggage() {
        this.id = "";
        this.ticketId = "";
        this.status = LuggageStatus.CHECKED_IN; // default
    }

    public Luggage(String id, String ticketId, LuggageStatus status) {
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

    public LuggageStatus getStatus() {
        return status;
    }

    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Luggage{" +
                "id='" + id + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", status=" + status +
                '}';
    }
}
