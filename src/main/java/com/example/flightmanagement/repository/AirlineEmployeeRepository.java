package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirlineEmployee;

public class AirlineEmployeeRepository extends InMemoryRepository<String, AirlineEmployee> {

    public AirlineEmployeeRepository() {
        super();
    }
    // poti adauga metode specifice dacă este nevoie
}
