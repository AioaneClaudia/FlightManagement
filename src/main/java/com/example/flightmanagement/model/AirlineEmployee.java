package com.example.flightmanagement.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class AirlineEmployee extends Staff {
    private String role; // ex "Pilot", "Crew"
    private List<FlightAssignment> assignments = new ArrayList<>();

    public AirlineEmployee() {
        super("", "");
        this.role = "";
    }

    public AirlineEmployee(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<FlightAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<FlightAssignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public String toString() {
        return "AirlineEmployee{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
