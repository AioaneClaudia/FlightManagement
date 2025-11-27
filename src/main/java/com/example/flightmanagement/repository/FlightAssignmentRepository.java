package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.FlightAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightAssignmentRepository extends JpaRepository<FlightAssignment, String> {
}
