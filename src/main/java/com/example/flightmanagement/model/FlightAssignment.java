package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "flight_assignments")
public class FlightAssignment {

    @Id
    @Column(length = 64)
    @NotBlank(message = "ID is required")
    private String id;

    @NotBlank(message = "Staff ID is required")
    private String staffId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    public FlightAssignment() {}

    public FlightAssignment(String id, Flight flight, String staffId) {
        this.id = id;
        this.flight = flight;
        this.staffId = staffId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
