package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @Column(length = 64)
    @NotBlank(message = "ID is required")
    private String id;

    @Positive(message = "Number must be positive")
    private int number;

    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 100, message = "Model must be between 2 and 100 characters")
    private String model;

    @Positive(message = "Capacity must be positive")
    private int capacity;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights = new ArrayList<>();

    public Airplane() {
        // constructor default — necesar pentru formulare și JPA
    }

    public Airplane(int number, String model, int capacity) {
        this.number = number;
        this.model = model;
        this.capacity = capacity;
    }

    // GETTERS & SETTERS

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Flight> getFlights() { return flights; }

    public void setFlights(List<Flight> flights) { this.flights = flights; }

    // Helper methods
    public void addFlight(Flight flight) {
        flight.setAirplane(this);
        flights.add(flight);
    }

    public void removeFlight(Flight flight) {
        flight.setAirplane(null);
        flights.remove(flight);
    }
}
