package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Airplane;

public class AirplaneRepository extends InMemoryRepository<String, Airplane> {

    public AirplaneRepository() {
        super();
    }

    // metode suplimentare specifice Airplane dacă exista
}
