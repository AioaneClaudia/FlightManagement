package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirportEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportEmployeeRepository extends JpaRepository<AirportEmployee, String> {
    // JpaRepository oferă toate operațiunile CRUD
}
