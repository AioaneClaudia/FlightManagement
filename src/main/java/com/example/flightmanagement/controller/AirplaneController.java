package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service;

    public AirplaneController(AirplaneService service) {
        this.service = service;
    }

    // List all airplanes
    @GetMapping
    public String list(Model model) {
        model.addAttribute("airplanes", service.getAllAirplanes());
        return "airplane/index";
    }

    // Show form for new airplane
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "airplane/form";
    }

    // Create new airplane
    @PostMapping
    public String create(@Valid @ModelAttribute("airplane") Airplane airplane, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "airplane/form";
        }
        service.addAirplane(airplane);
        return "redirect:/airplanes";
    }

    // Show form for editing airplane
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Airplane airplane = service.getAirplaneById(id);
        if (airplane == null) return "redirect:/airplanes";
        model.addAttribute("airplane", airplane);
        return "airplane/form";
    }

    // Update airplane
    @PostMapping("/{id}")
    public String update(@PathVariable String id, @Valid @ModelAttribute("airplane") Airplane airplane, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "airplane/form";
        }
        airplane.setId(id);
        service.addAirplane(airplane); // save (overwrite)
        return "redirect:/airplanes";
    }

    // Show details of airplane
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Airplane airplane = service.getAirplaneById(id);
        if (airplane == null) return "redirect:/airplanes";
        model.addAttribute("airplane", airplane);
        return "airplane/details";
    }

    // Delete airplane
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.removeAirplane(id);
        return "redirect:/airplanes";
    }
}
