package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @Column(length = 64)
    @NotBlank(message = "ID is required")
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2..100 characters")
    private String name;

    // ManyToOne - referință către NoticeBoard
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_board_id", nullable = false)
    @NotNull(message = "NoticeBoard is required")
    private NoticeBoard noticeBoard;

    @NotBlank(message = "AirplaneId is required")
    private String airplaneId;

    @NotNull(message = "Departure time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightAssignment> flightAssignments = new ArrayList<>();

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public Flight() {
    }

    public Flight(String id, String name, NoticeBoard noticeBoard, String airplaneId,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.name = name;
        this.noticeBoard = noticeBoard;
        this.airplaneId = airplaneId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // getters & setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public NoticeBoard getNoticeBoard() { return noticeBoard; }
    public void setNoticeBoard(NoticeBoard noticeBoard) { this.noticeBoard = noticeBoard; }

    public String getAirplaneId() { return airplaneId; }
    public void setAirplaneId(String airplaneId) { this.airplaneId = airplaneId; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public List<FlightAssignment> getFlightAssignments() { return flightAssignments; }
    public void setFlightAssignments(List<FlightAssignment> flightAssignments) { this.flightAssignments = flightAssignments; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    // helpers to keep both sides in sync
    public void addAssignment(FlightAssignment assignment) {
        assignment.setFlight(this);
        this.flightAssignments.add(assignment);
    }

    public void removeAssignment(FlightAssignment assignment) {
        assignment.setFlight(null);
        this.flightAssignments.remove(assignment);
    }

    public void addTicket(Ticket ticket) {
        ticket.setFlight(this);
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        ticket.setFlight(null);
        this.tickets.remove(ticket);
    }
}
