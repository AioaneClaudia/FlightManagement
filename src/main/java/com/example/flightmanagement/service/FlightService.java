package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.repository.FlightRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private FlightRepository flightRepository = new FlightRepository();

    public void addFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id);
    }

    public void removeFlight(String id) {
        flightRepository.delete(id);
    }

}
