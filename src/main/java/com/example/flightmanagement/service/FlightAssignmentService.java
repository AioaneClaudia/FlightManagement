package com.example.flightmanagement.service;

import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.repository.FlightAssignmentRepository;
import java.util.List;

public class FlightAssignmentService {
    private FlightAssignmentRepository assignmentRepository = new FlightAssignmentRepository();

    public void addAssignment(FlightAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public FlightAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public void removeAssignment(String id) {
        assignmentRepository.delete(id);
    }
}
