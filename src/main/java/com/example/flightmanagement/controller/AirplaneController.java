package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {

    private final AirplaneService service;

    public AirplaneController(AirplaneService service) {
        this.service = service;
    }

    // List all airplanes with filtering + sorting
    @GetMapping
    public String list(@RequestParam(required = false) String model,
                       @RequestParam(required = false) Integer capacityMin,
                       @RequestParam(required = false) Integer capacityMax,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       Model uiModel) {

        List<Airplane> airplanes = service.getFilteredAndSortedAirplanes(
                model,
                capacityMin,
                capacityMax,
                sortField,
                sortDir
        );

        uiModel.addAttribute("airplanes", airplanes);

        // Keep filters in UI
        uiModel.addAttribute("modelFilter", model);
        uiModel.addAttribute("capacityMinFilter", capacityMin);
        uiModel.addAttribute("capacityMaxFilter", capacityMax);

        // Sorting
        uiModel.addAttribute("sortField", sortField);
        uiModel.addAttribute("sortDir", sortDir);
        uiModel.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

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
        service.addAirplane(airplane);
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
