package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirportEmployee;
import org.springframework.stereotype.Repository;

@Repository
public class AirportEmployeeRepository extends InFileRepository<String, AirportEmployee> {
    public AirportEmployeeRepository() {
        super("src/main/resources/data/airportEmployees.json.json", AirportEmployee.class);
    }
}
