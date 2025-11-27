package com.example.flightmanagement.service;

import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightAssignmentService {

    private final FlightAssignmentRepository assignmentRepository;

    public FlightAssignmentService(FlightAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public void addAssignment(FlightAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public FlightAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void updateAssignment(String id, FlightAssignment assignment) {
        assignment.setId(id);
        assignmentRepository.save(assignment);
    }

    public void removeAssignment(String id) {
        assignmentRepository.deleteById(id);
    }
}
