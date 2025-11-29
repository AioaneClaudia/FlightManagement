package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.service.LuggageService;
import com.example.flightmanagement.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luggages")
public class LuggageController {

    private final LuggageService luggageService;
    private final TicketService ticketService;

    public LuggageController(LuggageService luggageService, TicketService ticketService) {
        this.luggageService = luggageService;
        this.ticketService = ticketService;
    }

    // Lista bagaje
    @GetMapping
    public String getAllLuggages(Model model) {
        model.addAttribute("luggages", luggageService.getAll());
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

        // Validare ticket selectat
        if (luggage.getTicket() == null || luggage.getTicket().getId() == null || luggage.getTicket().getId().isBlank()) {
            result.rejectValue("ticket", "error.luggage", "Ticket is required");
        } else {
            Ticket ticket = ticketService.getTicketById(luggage.getTicket().getId());
            if (ticket == null) {
                result.rejectValue("ticket", "error.luggage", "Selected ticket does not exist");
            } else {
                luggage.setTicket(ticket);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("tickets", ticketService.getAllTickets());
            return "luggage/form";
        }

        luggageService.add(luggage);
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

        // Validare ticket selectat
        if (luggage.getTicket() == null || luggage.getTicket().getId() == null || luggage.getTicket().getId().isBlank()) {
            result.rejectValue("ticket", "error.luggage", "Ticket is required");
        } else {
            Ticket ticket = ticketService.getTicketById(luggage.getTicket().getId());
            if (ticket == null) {
                result.rejectValue("ticket", "error.luggage", "Selected ticket does not exist");
            } else {
                luggage.setTicket(ticket);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("tickets", ticketService.getAllTickets());
            return "luggage/form";
        }

        luggageService.update(id, luggage);
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
