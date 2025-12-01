package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passenger/form";
    }

    @PostMapping
    public String addPassenger(@Valid @ModelAttribute Passenger passenger, BindingResult result) {
        if (result.hasErrors()) {
            return "passenger/form";
        }
        passengerService.createPassenger(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger == null) return "redirect:/passengers";
        model.addAttribute("passenger", passenger);
        return "passenger/form";
    }

    @PostMapping("/{id}")
    public String updatePassenger(@PathVariable String id, @Valid @ModelAttribute Passenger passenger, BindingResult result) {
        if (result.hasErrors()) {
            return "passenger/form";
        }
        passengerService.updatePassenger(id, passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/details")
    public String showPassengerDetails(@PathVariable String id, Model model) {
        Passenger passenger = passengerService.getPassengerById(id);
        if (passenger == null) return "redirect:/passengers";
        model.addAttribute("passenger", passenger);
        return "passenger/details";
    }

    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable String id) {
        passengerService.removePassenger(id);
        return "redirect:/passengers";
    }
}
