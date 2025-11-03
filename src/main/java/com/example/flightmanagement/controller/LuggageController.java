package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Luggage;
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
        model.addAttribute("luggage", new Luggage("", "", ""));
        return "luggage/form";
    }

    @PostMapping
    public String create(@ModelAttribute Luggage luggage) {
        luggageService.addLuggage(luggage);
        return "redirect:/luggages";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        luggageService.removeLuggage(id);
        return "redirect:/luggages";
    }
}
