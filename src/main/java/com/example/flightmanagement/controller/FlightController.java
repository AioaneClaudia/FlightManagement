package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Afișează toate zborurile
    @GetMapping
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "flight/index";
    }

    // Form pentru creare zbor nou
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "flight/form";
    }

    // Adaugă un zbor nou cu validare
    @PostMapping
    public String addFlight(@Valid @ModelAttribute Flight flight,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "flight/form";
        }
        flightService.addFlight(flight);
        return "redirect:/flights";
    }

    // Form pentru editare zbor
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return "redirect:/flights";
        }
        model.addAttribute("flight", flight);
        return "flight/form";
    }

    // Actualizează zborul cu validare
    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable String id,
                               @Valid @ModelAttribute Flight flight,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "flight/form";
        }
        flight.setId(id); // ne asigurăm că ID-ul rămâne același
        flightService.addFlight(flight); // save face insert sau update
        return "redirect:/flights";
    }

    // Șterge un zbor după ID
    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable String id) {
        flightService.removeFlight(id);
        return "redirect:/flights";
    }

    // Detalii zbor
    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return "redirect:/flights";
        }
        model.addAttribute("flight", flight);
        return "flight/details";
    }
}
