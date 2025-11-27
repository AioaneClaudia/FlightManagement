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
    public String addAssignment(@Valid @ModelAttribute FlightAssignment assignment,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("flights", flightService.getAllFlights());
            return "assignment/form";
        }
        assignmentService.addAssignment(assignment);
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
                                   @Valid @ModelAttribute FlightAssignment assignment,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("flights", flightService.getAllFlights());
            return "assignment/form";
        }
        assignment.setId(id);
        assignmentService.addAssignment(assignment); // save face insert/update
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
