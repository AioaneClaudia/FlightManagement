package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.FlightAssignment;
import com.example.flightmanagement.service.FlightAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class FlightAssignmentController {

    private final FlightAssignmentService assignmentService = new FlightAssignmentService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "assignment/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("assignment", new FlightAssignment("", "", ""));
        return "assignment/form";
    }

    @PostMapping
    public String create(@ModelAttribute FlightAssignment assignment) {
        assignmentService.addAssignment(assignment);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        assignmentService.removeAssignment(id);
        return "redirect:/assignments";
    }
}
