package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;

    public AirlineEmployeeController(AirlineEmployeeService service) {
        this.service = service;
    }

    // List all employees
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "airlineemployee/index";
    }

    // Show form for new employee
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("employee", new AirlineEmployee("", "", ""));
        return "airlineemployee/form";
    }

    // Create a new employee
    @PostMapping
    public String create(@ModelAttribute AirlineEmployee employee) {
        service.addEmployee(employee);
        return "redirect:/airlineemployees";
    }

    // Show form for editing
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "airlineemployee/form";
    }

    // Update employee
    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute AirlineEmployee employee) {
        employee.setId(id);
        service.addEmployee(employee); // save will overwrite
        return "redirect:/airlineemployees";
    }

    // Show details
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "airlineemployee/details";
    }

    // Delete employee
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.removeEmployee(id);
        return "redirect:/airlineemployees";
    }
}
