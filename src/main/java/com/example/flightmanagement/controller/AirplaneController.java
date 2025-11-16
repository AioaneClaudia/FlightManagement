package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service = new AirplaneService();

    // List all airplanes
    @GetMapping
    public String list(Model model) {
        model.addAttribute("airplanes", service.getAllAirplanes());
        return "airplane/index";
    }

    // Show form for new airplane
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplane", new Airplane("", 0, "", 0));
        return "airplane/form";
    }

    // Create new airplane
    @PostMapping
    public String create(@ModelAttribute Airplane airplane) {
        service.addAirplane(airplane);
        return "redirect:/airplanes";
    }

    // Show form for editing airplane
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("airplane", service.getAirplaneById(id));
        return "airplane/form";
    }

    // Update airplane
    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Airplane airplane) {
        airplane.setId(id);
        service.addAirplane(airplane); // save will overwrite existing airplane
        return "redirect:/airplanes";
    }

    // Show details of airplane
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("airplane", service.getAirplaneById(id));
        return "airplane/details";
    }

    // Delete airplane
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.removeAirplane(id);
        return "redirect:/airplanes";
    }
}
