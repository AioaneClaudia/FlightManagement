package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService = new PassengerService();

    @GetMapping
    public String getAllPassengers(Model model) {
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("passenger", new Passenger("", "", ""));
        return "passenger/form";
    }

    @PostMapping
    public String addPassenger(@ModelAttribute Passenger passenger) {
        passengerService.registerPassenger(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("passenger", passengerService.getPassengerById(id));
        return "passenger/form";
    }

    @PostMapping("/{id}")
    public String updatePassenger(@PathVariable String id, @ModelAttribute Passenger passenger) {
        passenger.setId(id);
        passengerService.registerPassenger(passenger);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/details")
    public String showPassengerDetails(@PathVariable String id, Model model) {
        model.addAttribute("passenger", passengerService.getPassengerById(id));
        return "passenger/details";
    }

    @PostMapping("/{id}/delete")
    public String deletePassenger(@PathVariable String id) {
        passengerService.removePassenger(id);
        return "redirect:/passengers";
    }
}
