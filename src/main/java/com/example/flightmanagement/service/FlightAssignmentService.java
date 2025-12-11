package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.repository.FlightAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    // --- Filtrare și sortare ---
    public List<FlightAssignment> getFilteredAndSorted(
            String idFilter,
            String staffIdFilter,
            String flightFilter,
            String sortField,
            String sortDir
    ) {
        List<FlightAssignment> assignments = assignmentRepository.findAll().stream()
                .filter(a -> idFilter == null || a.getId().toLowerCase().contains(idFilter.toLowerCase()))
                .filter(a -> staffIdFilter == null || a.getStaffId().toLowerCase().contains(staffIdFilter.toLowerCase()))
                .filter(a -> flightFilter == null ||
                        (a.getFlight() != null &&
                                (a.getFlight().getId().toLowerCase().contains(flightFilter.toLowerCase()) ||
                                        a.getFlight().getName().toLowerCase().contains(flightFilter.toLowerCase()))
                        )
                )
                .collect(Collectors.toList());

        Comparator<FlightAssignment> comparator;
        switch (sortField) {
            case "staffId":
                comparator = Comparator.comparing(FlightAssignment::getStaffId, String.CASE_INSENSITIVE_ORDER);
                break;
            case "flight":
                comparator = Comparator.comparing(a -> a.getFlight() != null ? a.getFlight().getId() : "",
                        String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                comparator = Comparator.comparing(FlightAssignment::getId, String.CASE_INSENSITIVE_ORDER);
        }

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        assignments.sort(comparator);
        return assignments;
    }
}
