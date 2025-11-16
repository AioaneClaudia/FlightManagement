package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InFileRepository<String, Passenger> {
    public PassengerRepository() {
        super("src/main/resources/data/Passenger.json", Passenger.class);
    }
}