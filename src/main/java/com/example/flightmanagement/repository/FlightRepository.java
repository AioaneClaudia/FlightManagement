package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Flight;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepository extends InFileRepository<String, Flight> {
    public FlightRepository() {
        super("src/main/resources/data/Flight.json", Flight.class);
    }
}