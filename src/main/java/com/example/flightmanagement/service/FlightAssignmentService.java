package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightAssignmentService {

    private final FlightAssignmentRepository assignmentRepository;
    private final FlightService flightService;

    public FlightAssignmentService(FlightAssignmentRepository assignmentRepository, FlightService flightService) {
        this.assignmentRepository = assignmentRepository;
        this.flightService = flightService;
    }

    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public FlightAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void removeAssignment(String id) {
        assignmentRepository.deleteById(id);
    }

    /**
     * Validare și setare Flight pe FlightAssignment
     * Aruncă IllegalArgumentException dacă Flight-ul nu există sau nu e setat
     */
    public void validateAndSetFlight(FlightAssignment assignment) {
        String flightId = assignment.getFlight() != null ? assignment.getFlight().getId() : null;

        if (flightId == null || flightId.isBlank()) {
            throw new IllegalArgumentException("Flight is required");
        }

        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            throw new IllegalArgumentException("Selected Flight does not exist");
        }

        assignment.setFlight(flight);
    }

    public void addAssignment(FlightAssignment assignment) {
        validateAndSetFlight(assignment);
        assignmentRepository.save(assignment);
    }

    public void updateAssignment(String id, FlightAssignment assignment) {
        assignment.setId(id);
        validateAndSetFlight(assignment);
        assignmentRepository.save(assignment);
    }
}
