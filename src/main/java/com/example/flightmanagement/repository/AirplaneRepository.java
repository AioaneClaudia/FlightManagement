package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Airplane;
import org.springframework.stereotype.Repository;

@Repository
public class AirplaneRepository extends InFileRepository<String, Airplane> {
    public AirplaneRepository() {
        super("src/main/resources/data/airlineEmployees.json", Airplane.class);
    }
}
