package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.LuggageStatus;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.service.LuggageService;
import com.example.flightmanagement.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/luggages")
public class LuggageController {

    private final LuggageService luggageService;
    private final TicketService ticketService;

    public LuggageController(LuggageService luggageService, TicketService ticketService) {
        this.luggageService = luggageService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String listLuggages(
            Model model,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ticketId,
            @RequestParam(required = false) LuggageStatus status,
            @RequestParam(required = false) Integer weightFrom,
            @RequestParam(required = false) Integer weightTo,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("message") String message
    ) {
        List<Luggage> luggages = luggageService.getFilteredAndSorted(type, ticketId, status, weightFrom, weightTo, sortField, sortDir);

        model.addAttribute("luggages", luggages);
        model.addAttribute("typeFilter", type);
        model.addAttribute("ticketIdFilter", ticketId);
        model.addAttribute("statusFilter", status);
        model.addAttribute("weightFrom", weightFrom);
        model.addAttribute("weightTo", weightTo);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        if (message != null && !message.isBlank()) {
            model.addAttribute("message", message);
        }

        return "luggage/index";
    }



    // Form pentru creare bagaj nou
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("luggage", new Luggage());
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "luggage/form";
    }

    // Adaugă bagaj nou
    @PostMapping("/new")
    public String addLuggage(@Valid @ModelAttribute("luggage") Luggage luggage,
                             BindingResult result,
                             Model model) {

        try {
            luggageService.add(luggage);
        } catch (IllegalArgumentException e) {
            result.rejectValue("ticketId", "error.luggage", e.getMessage());
        }

        if (result.hasErrors()) {
            model.addAttribute("tickets", ticketService.getAllTickets());
            return "luggage/form";
        }

        return "redirect:/luggages";
    }

    // Form pentru editare bagaj
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.getById(id);
        if (luggage == null) return "redirect:/luggages";

        model.addAttribute("luggage", luggage);
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "luggage/form";
    }

    // Actualizare bagaj
    @PostMapping("/{id}/edit")
    public String updateLuggage(@PathVariable String id,
                                @Valid @ModelAttribute("luggage") Luggage luggage,
                                BindingResult result,
                                Model model) {

        try {
            luggageService.update(id, luggage);
        } catch (IllegalArgumentException e) {
            result.rejectValue("ticketId", "error.luggage", e.getMessage());
        }

        if (result.hasErrors()) {
            model.addAttribute("tickets", ticketService.getAllTickets());
            return "luggage/form";
        }

        return "redirect:/luggages";
    }

    // Ștergere bagaj
    @PostMapping("/{id}/delete")
    public String deleteLuggage(@PathVariable String id) {
        luggageService.delete(id);
        return "redirect:/luggages";
    }

    // Detalii bagaj
    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Luggage luggage = luggageService.getById(id);
        if (luggage == null) return "redirect:/luggages";

        model.addAttribute("luggage", luggage);
        return "luggage/details";
    }
}
