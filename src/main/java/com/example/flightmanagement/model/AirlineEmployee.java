package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airline_employee")
public class AirlineEmployee extends Staff {

    @NotBlank(message = "Role darf nicht leer sein")
    @Size(max = 100, message = "Role darf höchstens 100 Zeichen haben")
    private String role; // ex "Pilot", "Crew"

    // momentan transient, später als eigene Entity + OneToMany abbilden
    @Transient
    private List<Object> assignments = new ArrayList<>();

    public AirlineEmployee() {
        super();
        this.role = "";
    }

    public AirlineEmployee(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Object> getAssignments() { return assignments; }
    public void setAssignments(List<Object> assignments) { this.assignments = assignments; }

    @Override
    public String toString() {
        return "AirlineEmployee{" + "id='" + getId() + '\'' + ", name='" + getName() + '\'' + ", role='" + role + '\'' + '}';
    }
}
