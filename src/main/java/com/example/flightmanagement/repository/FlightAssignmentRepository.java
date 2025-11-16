package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.FlightAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class FlightAssignmentRepository extends InFileRepository<String, FlightAssignment> {
    public FlightAssignmentRepository() {
        super("src/main/resources/data/flightAssignment.json", FlightAssignment.class);
    }
}
