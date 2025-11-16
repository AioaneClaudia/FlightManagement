package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService = new TicketService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("ticket", new Ticket("", "", "", 0.0, ""));
        return "ticket/form";
    }

    @PostMapping
    public String create(@ModelAttribute Ticket ticket) {
        ticketService.issueTicket(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        return "ticket/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Ticket ticket) {
        ticket.setId(id);
        ticketService.issueTicket(ticket);   // save = update
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        return "ticket/details";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        ticketService.cancelTicket(id);
        return "redirect:/tickets";
    }
}
