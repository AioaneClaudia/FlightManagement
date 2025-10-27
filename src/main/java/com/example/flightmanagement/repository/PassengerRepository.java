package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Passenger;
import java.util.*;

public class PassengerRepository {
    private Map<String, Passenger> passengers = new HashMap<>();

    public void save(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
    }

    public List<Passenger> findAll() {
        return new ArrayList<>(passengers.values());
    }

    public Passenger findById(String id) {
        return passengers.get(id);
    }

    public void delete(String id) {
        passengers.remove(id);
    }
}
