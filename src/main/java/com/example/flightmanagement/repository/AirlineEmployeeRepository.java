package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirlineEmployee;
import org.springframework.stereotype.Repository;

@Repository
public class AirlineEmployeeRepository extends InFileRepository<String, AirlineEmployee> {
    public AirlineEmployeeRepository() {
        super("src/main/resources/data/airlineEmployees.json", AirlineEmployee.class);
    }
}

