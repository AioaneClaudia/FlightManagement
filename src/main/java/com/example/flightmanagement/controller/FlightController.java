package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService = new FlightService();

    @GetMapping
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "flight/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("flight", new Flight("", "", "", "0"));
        return "flight/form";
    }

    @PostMapping
    public String addFlight(@ModelAttribute Flight flight) {
        flightService.addFlight(flight);
        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable String id) {
        flightService.removeFlight(id);
        return "redirect:/flights";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        model.addAttribute("flight", flight);
        return "flight/details";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        model.addAttribute("flight", flight);
        return "flight/form"; // putem folosi același form.html
    }

    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable String id, @ModelAttribute Flight flight) {
        flight.setId(id); // ne asigurăm că ID-ul rămâne același
        flightService.addFlight(flight); // suprascriem entitatea existentă
        return "redirect:/flights";
    }

}
