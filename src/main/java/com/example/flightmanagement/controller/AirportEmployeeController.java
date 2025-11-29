package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirportEmployee;
import com.example.flightmanagement.service.AirportEmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airportemployees")
public class AirportEmployeeController {

    private final AirportEmployeeService service;

    public AirportEmployeeController(AirportEmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "airportemployee/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        AirportEmployee employee = new AirportEmployee();
        model.addAttribute("employee", employee);
        model.addAttribute("formAction", "/airportemployees");
        model.addAttribute("isEdit", false);
        return "airportemployee/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("employee") AirportEmployee employee,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/airportemployees");
            model.addAttribute("isEdit", false);
            return "airportemployee/form";
        }
        try {
            service.addEmployee(employee);
        } catch (IllegalArgumentException e) {
            model.addAttribute("globalError", e.getMessage());
            model.addAttribute("formAction", "/airportemployees");
            model.addAttribute("isEdit", false);
            return "airportemployee/form";
        }
        return "redirect:/airportemployees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        AirportEmployee emp = service.getEmployeeById(id);
        if (emp == null) {
            model.addAttribute("globalError", "Employee not found");
            return "airportemployee/index";
        }
        model.addAttribute("employee", emp);
        model.addAttribute("formAction", "/airportemployees/" + id);
        model.addAttribute("isEdit", true);
        return "airportemployee/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("employee") AirportEmployee employee,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/airportemployees/" + id);
            model.addAttribute("isEdit", true);
            return "airportemployee/form";
        }
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
