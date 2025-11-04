package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirlineEmployee;
import org.springframework.stereotype.Repository;

@Repository  // Spring va gestiona singleton-ul
public class AirlineEmployeeRepository extends InMemoryRepository<String, AirlineEmployee> {
    public AirlineEmployeeRepository() {
        super();
    }
}
