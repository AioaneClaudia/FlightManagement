package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.FlightAssignment;
import java.util.*;

public class FlightAssignmentRepository {
    private Map<String, FlightAssignment> assignments = new HashMap<>();

    public void save(FlightAssignment assignment) {
        assignments.put(assignment.getId(), assignment);
    }

    public List<FlightAssignment> findAll() {
        return new ArrayList<>(assignments.values());
    }

    public FlightAssignment findById(String id) {
        return assignments.get(id);
    }

    public void delete(String id) {
        assignments.remove(id);
    }
}
