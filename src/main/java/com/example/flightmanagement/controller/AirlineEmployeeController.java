package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.service.AirlineEmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airlineemployees")
public class AirlineEmployeeController {

    private final AirlineEmployeeService service;

    public AirlineEmployeeController(AirlineEmployeeService service) {
        this.service = service;
    }

    // LISTA ANGAJATI
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "airlineemployee/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        AirlineEmployee employee = new AirlineEmployee();
        model.addAttribute("employee", employee);
        model.addAttribute("formAction", "/airlineemployees"); // URL pentru create
        model.addAttribute("isEdit", false); // folosit pentru readonly ID
        return "airlineemployee/form";
    }

    // CREARE ANGAJAT
    @PostMapping
    public String create(@Valid @ModelAttribute("employee") AirlineEmployee employee,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "airlineemployee/form";
        }

        // Verificăm dacă ID-ul există deja
        if (service.getEmployeeById(employee.getId()) != null) {
            model.addAttribute("globalError", "Employee with this ID already exists");
            return "airlineemployee/form";
        }

        service.addEmployee(employee);
        return "redirect:/airlineemployees";
    }

    // FORMULAR PENTRU EDITARE
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        AirlineEmployee emp = service.getEmployeeById(id);
        if (emp == null) {
            model.addAttribute("globalError", "Employee not found");
            return "airlineemployee/index";
        }
        model.addAttribute("employee", emp);
        model.addAttribute("formAction", "/airlineemployees/" + id); // URL pentru update
        model.addAttribute("isEdit", true); // folosit pentru readonly ID
        return "airlineemployee/form";
    }

    // ACTUALIZARE ANGAJAT
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("employee") AirlineEmployee employee,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "airlineemployee/form";
        }

        employee.setId(id); // ne asigurăm că ID-ul rămâne același
        service.addEmployee(employee); // actualizare prin metoda service
        return "redirect:/airlineemployees";
    }

    // DETALII ANGAJAT
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        AirlineEmployee emp = service.getEmployeeById(id);
        if (emp == null) {
            model.addAttribute("globalError", "Employee not found");
            return "airlineemployee/index";
        }
        model.addAttribute("employee", emp);
        return "airlineemployee/details";
    }

    // STERGERE ANGAJAT
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.removeEmployee(id);
        return "redirect:/airlineemployees";
    }
}
