package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Passenger;

public class PassengerRepository extends InMemoryRepository<String, Passenger> {

    public PassengerRepository() {
        super();
    }
    // poti adauga metode specifice dacă este nevoie
}