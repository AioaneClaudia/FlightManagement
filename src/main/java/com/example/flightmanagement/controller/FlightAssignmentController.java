package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.service.FlightAssignmentService;
import com.example.flightmanagement.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class FlightAssignmentController {

    private final FlightAssignmentService assignmentService;
    private final FlightService flightService;

    public FlightAssignmentController(FlightAssignmentService assignmentService, FlightService flightService) {
        this.assignmentService = assignmentService;
        this.flightService = flightService;
    }

    // Lista assignment-uri
    @GetMapping
    public String getAllAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "assignment/index";
    }

    // Form pentru creare assignment nou
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new FlightAssignment());
        model.addAttribute("flights", flightService.getAllFlights());
        return "assignment/form";
    }

    // Adaugă assignment nou
    @PostMapping
    public String addAssignment(@Valid @ModelAttribute("assignment") FlightAssignment assignment,
                                BindingResult result,
                                Model model) {

        model.addAttribute("flights", flightService.getAllFlights());

        String flightId = assignment.getFlight() != null ? assignment.getFlight().getId() : null;

        if (flightId == null || flightId.isBlank()) {
            result.rejectValue("flight", "NotNull", "Flight is required");
        } else {
            Flight flight = flightService.getFlightById(flightId);
            if (flight == null) {
                result.rejectValue("flight", "NotFound", "Selected Flight does not exist");
            } else {
                assignment.setFlight(flight);
            }
        }

        if (result.hasErrors()) {
            return "assignment/form";
        }

        try {
            assignmentService.addAssignment(assignment);
        } catch (IllegalArgumentException ex) {
            result.reject("businessError", ex.getMessage());
            return "assignment/form";
        }

        return "redirect:/assignments";
    }



    // Form pentru editare assignment
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        FlightAssignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) {
            return "redirect:/assignments";
        }
        model.addAttribute("assignment", assignment);
        model.addAttribute("flights", flightService.getAllFlights());
        return "assignment/form";
    }

    // Actualizare assignment
    @PostMapping("/{id}/edit")
    public String updateAssignment(@PathVariable String id,
                                   @Valid @ModelAttribute("assignment") FlightAssignment assignment,
                                   BindingResult result,
                                   Model model) {

        model.addAttribute("flights", flightService.getAllFlights());

        FlightAssignment existing = assignmentService.getAssignmentById(id);
        if (existing == null) {
            return "redirect:/assignments";
        }

        assignment.setId(id);

        String flightId = assignment.getFlight() != null ? assignment.getFlight().getId() : null;

        if (flightId == null || flightId.isBlank()) {
            result.rejectValue("flight", "NotNull", "Flight is required");
        } else {
            Flight flight = flightService.getFlightById(flightId);
            if (flight == null) {
                result.rejectValue("flight", "NotFound", "Selected Flight does not exist");
            } else {
                assignment.setFlight(flight);
            }
        }

        if (result.hasErrors()) {
            return "assignment/form";
        }

        try {
            assignmentService.addAssignment(assignment);
        } catch (IllegalArgumentException ex) {
            result.reject("businessError", ex.getMessage());
            return "assignment/form";
        }

        return "redirect:/assignments";
    }



    // Ștergere assignment
    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable String id) {
        assignmentService.removeAssignment(id);
        return "redirect:/assignments";
    }

    // Detalii assignment
    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        FlightAssignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) {
            return "redirect:/assignments";
        }
        model.addAttribute("assignment", assignment);
        return "assignment/details";
    }
}
