package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirlineEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineEmployeeRepository extends JpaRepository<AirlineEmployee, String> {
    // zusätzliche Query-Methoden falls nötig, z.B. findByRole(...)
}
