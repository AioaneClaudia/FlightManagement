package com.example.flightmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Airplane {
    private String id;
    private int number;
    private String model; // new field
    private int capacity; // new field
    private List<Flight> flights = new ArrayList<>();

    public Airplane() {
        this.id = "";
        this.number = 0;
        this.model = "";
        this.capacity = 0;
    }

    public Airplane(String id, int number, String model, int capacity) {
        this.id = id;
        this.number = number;
        this.model = model;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
