package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Flight;

public class FlightRepository extends InMemoryRepository<String, Flight> {

    public FlightRepository() {
        super();
    }

}
