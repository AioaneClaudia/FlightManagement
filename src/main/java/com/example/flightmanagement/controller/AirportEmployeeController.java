package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirportEmployee;
import com.example.flightmanagement.service.AirportEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airportemployees")
public class AirportEmployeeController {

    private final AirportEmployeeService service = new AirportEmployeeService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "airportemployee/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("employee", new AirportEmployee("", "", "", ""));
        return "airportemployee/form";
    }

    @PostMapping
    public String create(@ModelAttribute AirportEmployee employee) {
        service.addEmployee(employee);
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "airportemployee/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute AirportEmployee employee) {
        employee.setId(id);
        service.updateEmployee(employee);
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("employee", service.getEmployeeById(id));
        return "airportemployee/details";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.removeEmployee(id);
        return "redirect:/airportemployees";
    }
}
