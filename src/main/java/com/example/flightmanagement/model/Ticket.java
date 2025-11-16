package com.example.flightmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String id;
    private String passengerId;
    private String flightId;
    private double price;
    private String seatNumber;
    private List<Luggage> luggages = new ArrayList<>();

    public Ticket() {
        this.id = "";
        this.passengerId = "";
        this.flightId = "";
        this.price = 0;
        this.seatNumber = "";
    }

    public Ticket(String id, String passengerId, String flightId, double price, String seatNumber) {
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.price = price;
        this.seatNumber = seatNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getPassengerId() {

        return passengerId;
    }

    public void setPassengerId(String passengerId) {

        this.passengerId = passengerId;
    }

    public String getFlightId() {

        return flightId;
    }

    public void setFlightId(String flightId) {

        this.flightId = flightId;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public String getSeatNumber() {

        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {

        this.seatNumber = seatNumber;
    }

    public List<Luggage> getLuggages() {

        return luggages;
    }

    public void setLuggages(List<Luggage> luggages) {

        this.luggages = luggages;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", price=" + price +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }
}
