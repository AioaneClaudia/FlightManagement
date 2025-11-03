package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService employeeService;

    public AirlineEmployeeController(AirlineEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /employees - afișează toți angajații
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/index";
    }

    // GET /employees/new - formular pentru adăugarea unui nou angajat
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new AirlineEmployee("", "", ""));
        return "employee/form";
    }

    // POST /employees - creează un nou angajat
    @PostMapping
    public String createEmployee(@ModelAttribute AirlineEmployee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/employees";
    }

    // POST /employees/{id}/delete - șterge un angajat
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.removeEmployee(id);
        return "redirect:/employees";
    }
}
