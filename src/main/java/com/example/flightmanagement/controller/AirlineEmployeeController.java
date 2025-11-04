package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.service.AirlineEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService employeeService;

    public AirlineEmployeeController(AirlineEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /employees - afișează toți angajații
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("airlineemployees", employeeService.getAllEmployees());
        return "airlineemployee/index";
    }


    // GET /employees/new - formular pentru adăugarea unui nou angajat
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("airlineemployee", new AirlineEmployee("", "", ""));
        return "airlineemployee/form";
    }

    // POST /employees - creează un nou angajat
    @PostMapping
    public String createEmployee(@ModelAttribute("airlineemployee") AirlineEmployee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/airlineemployees";
    }


    // POST /employees/{id}/delete - șterge un angajat
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.removeEmployee(id);
        return "redirect:/airlineemployees";
    }
}
