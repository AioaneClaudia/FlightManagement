package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 64)
    private String id;

    @NotBlank(message = "Passenger name is required.")
    private String passengerName;

    @NotNull(message = "Price is required.")
    private Double price;

    @NotBlank(message = "Passenger ID is required.")
    private String passengerId;

    @NotBlank(message = "Seat number is required.")
    private String seatNumber;

    // RELAȚIE MANY-TO-ONE CU Flight
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_fk", nullable = true)
    private Passenger passenger;


    @Transient
    private String flightId;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Luggage> luggages = new ArrayList<>();

    // GETTERS & SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public List<Luggage> getLuggages() { return luggages; }
    public void setLuggages(List<Luggage> luggages) { this.luggages = luggages; }

    public void addLuggage(Luggage luggage) {
        luggages.add(luggage);
        luggage.setTicket(this);
    }

    public void removeLuggage(Luggage luggage) {
        luggages.remove(luggage);
        luggage.setTicket(null);
    }
}