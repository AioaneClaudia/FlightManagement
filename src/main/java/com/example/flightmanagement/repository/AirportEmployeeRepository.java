package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirportEmployee;

public class AirportEmployeeRepository extends InMemoryRepository<String, AirportEmployee> {

    public AirportEmployeeRepository() {
        super();
    }
    // poti adauga metode specifice dacă este nevoie
}
