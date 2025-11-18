package com.example.flightmanagement.service;

import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightAssignmentService {

    private final FlightAssignmentRepository assignmentRepository = new FlightAssignmentRepository();

    public void addAssignment(FlightAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public FlightAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public void updateAssignment(String id, FlightAssignment assignment) {
        // setăm ID-ul (în caz că formularul nu îl trimite)
        assignment.setId(id);
        assignmentRepository.save(assignment); // save() suprascrie
    }

    public void removeAssignment(String id) {
        assignmentRepository.delete(id);
    }
}
