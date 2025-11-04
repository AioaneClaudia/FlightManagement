package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService = new AirplaneService();

    // GET all - afișează lista de avioane
    @GetMapping
    public String getAllAirplanes(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        return "airplane/index";
    }

    // GET /airplanes/new - afișează formularul de creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("airplane", new Airplane("", 0, "", 0));
        return "airplane/form";
    }

    // POST /airplanes - procesează formularul și adaugă avionul
    @PostMapping
    public String addAirplane(@ModelAttribute Airplane airplane) {
        airplaneService.addAirplane(airplane);
        return "redirect:/airplanes";
    }

    // POST /airplanes/{id}/delete - șterge avionul cu id-ul dat
    @PostMapping("/{id}/delete")
    public String deleteAirplane(@PathVariable String id) {
        airplaneService.removeAirplane(id);
        return "redirect:/airplanes";
    }
}


