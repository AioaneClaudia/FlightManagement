package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.LuggageStatus;
import com.example.flightmanagement.service.LuggageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luggages")
public class LuggageController {

    private final LuggageService luggageService = new LuggageService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("luggages", luggageService.getAllLuggages());
        return "luggage/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("luggage", new Luggage("", "", LuggageStatus.CHECKED_IN));
        return "luggage/form";
    }

    @PostMapping
    public String create(@ModelAttribute Luggage luggage) {
        luggageService.addLuggage(luggage);
        return "redirect:/luggages";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("luggage", luggageService.getLuggageById(id));
        return "luggage/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Luggage luggage) {
        luggage.setId(id);
        luggageService.addLuggage(luggage); // save = update
        return "redirect:/luggages";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("luggage", luggageService.getLuggageById(id));
        return "luggage/details";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        luggageService.removeLuggage(id);
        return "redirect:/luggages";
    }
}
