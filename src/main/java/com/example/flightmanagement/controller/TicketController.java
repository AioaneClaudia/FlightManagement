package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.service.FlightService;
import com.example.flightmanagement.service.TicketService;
import com.example.flightmanagement.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;


    public TicketController(TicketService ticketService, FlightService flightService, PassengerService passengerService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("passengers", passengerService.getAllPassengers());

        return "ticket/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Ticket ticket,
                         BindingResult result,
                         Model model) {

        // VALIDARE FLIGHT
        if (ticket.getFlightId() == null || ticket.getFlightId().isBlank()) {
            result.rejectValue("flightId", "error.ticket", "Flight is required");
        } else {
            Flight flight = flightService.getFlightById(ticket.getFlightId());
            if (flight == null) {
                result.rejectValue("flightId", "error.ticket", "Selected flight does not exist");
            } else {
                ticket.setFlight(flight);
            }
        }

        // VALIDARE PASSENGER
        if (ticket.getPassengerId() == null || ticket.getPassengerId().isBlank()) {
            result.rejectValue("passengerId", "error.ticket", "Passenger is required");
        } else {
            var passenger = passengerService.getPassengerById(ticket.getPassengerId());
            if (passenger == null) {
                result.rejectValue("passengerId", "error.ticket", "Selected passenger does not exist");
            } else {
                ticket.setPassenger(passenger);
            }
        }

        if (result.hasErrors()) {
            return "ticket/form"; // nu mai e nevoie de flights/passengers aici
        }

        ticketService.issueTicket(ticket);
        return "redirect:/tickets";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            return "redirect:/tickets";
        }
        ticket.setFlightId(ticket.getFlight().getId());
        model.addAttribute("ticket", ticket);
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("passengers", passengerService.getAllPassengers());

        return "ticket/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Ticket ticket,
                         BindingResult result,
                         Model model) {

        // VALIDARE FLIGHT
        if (ticket.getFlightId() == null || ticket.getFlightId().isBlank()) {
            result.rejectValue("flightId", "error.ticket", "Flight is required");
        } else {
            Flight flight = flightService.getFlightById(ticket.getFlightId());
            if (flight == null) {
                result.rejectValue("flightId", "error.ticket", "Selected flight does not exist");
            } else {
                ticket.setFlight(flight);
            }
        }

        // VALIDARE PASSENGER
        if (ticket.getPassengerId() == null || ticket.getPassengerId().isBlank()) {
            result.rejectValue("passengerId", "error.ticket", "Passenger is required");
        } else {
            var passenger = passengerService.getPassengerById(ticket.getPassengerId());
            if (passenger == null) {
                result.rejectValue("passengerId", "error.ticket", "Selected passenger does not exist");
            } else {
                ticket.setPassenger(passenger);
            }
        }

        if (result.hasErrors()) {
            return "ticket/form";
        }

        ticketService.updateTicket(id, ticket);
        return "redirect:/tickets";
    }


    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            return "redirect:/tickets";
        }
        ticket.getLuggages().size();
        model.addAttribute("ticket", ticket);
        return "ticket/details";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        ticketService.cancelTicket(id);
        return "redirect:/tickets";
    }
}
