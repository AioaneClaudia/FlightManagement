package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Flight;
import java.util.*;

public class FlightRepository {
    private Map<String, Flight> flights = new HashMap<>();

    public void save(Flight flight) {
        flights.put(flight.getId(), flight);
    }

    public List<Flight> findAll() {
        return new ArrayList<>(flights.values());
    }

    public Flight findById(String id) {
        return flights.get(id);
    }

    public void delete(String id) {
        flights.remove(id);
    }
}
