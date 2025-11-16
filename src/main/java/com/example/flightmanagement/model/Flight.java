package com.example.flightmanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String id;
    private String name;
    private String noticeBoardId;
    private String airplaneId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Ticket> tickets;
    private List<FlightAssignment> flightAssignments;

    // Constructor fără parametri – necesar pentru Jackson
    public Flight() {
        this.id = "";
        this.name = "";
        this.noticeBoardId = "";
        this.airplaneId = "";
        this.tickets = new ArrayList<>();
        this.flightAssignments = new ArrayList<>();
    }

    // Constructor cu parametri
    public Flight(String id, String name, String noticeBoardId, String airplaneId) {
        this.id = id;
        this.name = name;
        this.noticeBoardId = noticeBoardId;
        this.airplaneId = airplaneId;
        this.tickets = new ArrayList<>();
        this.flightAssignments = new ArrayList<>();
    }

    // Getteri și setteri
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNoticeBoardId() { return noticeBoardId; }
    public void setNoticeBoardId(String noticeBoardId) { this.noticeBoardId = noticeBoardId; }

    public String getAirplaneId() { return airplaneId; }
    public void setAirplaneId(String airplaneId) { this.airplaneId = airplaneId; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public List<FlightAssignment> getFlightAssignments() { return flightAssignments; }
    public void setFlightAssignments(List<FlightAssignment> flightAssignments) { this.flightAssignments = flightAssignments; }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", noticeBoardId='" + noticeBoardId + '\'' +
                ", airplaneId='" + airplaneId + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", tickets=" + tickets +
                ", flightAssignments=" + flightAssignments +
                '}';
    }
}
