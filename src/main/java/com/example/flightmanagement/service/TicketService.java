package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void issueTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void updateTicket(String id, Ticket ticket) {
        ticket.setId(id);
        ticketRepository.save(ticket);
    }

    public void cancelTicket(String id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> filterAndSortTickets(String passengerName, Double minPrice, Double maxPrice,
                                             String sortField, String sortDir) {
        // Obținem toate biletele și filtrăm
        List<Ticket> tickets = ticketRepository.findAll().stream()
                .filter(t -> (passengerName == null || passengerName.isBlank() || t.getPassengerName().toLowerCase().contains(passengerName.toLowerCase())))
                .filter(t -> (minPrice == null || t.getPrice() >= minPrice))
                .filter(t -> (maxPrice == null || t.getPrice() <= maxPrice))
                .collect(Collectors.toList());

        // Sortare după câmp și direcție
        if (sortField != null && !sortField.isBlank()) {
            Comparator<Ticket> comparator = switch (sortField) {
                case "passengerName" -> Comparator.comparing(Ticket::getPassengerName, String.CASE_INSENSITIVE_ORDER);
                case "price" -> Comparator.comparing(Ticket::getPrice);
                default -> Comparator.comparing(Ticket::getId);
            };
            if ("desc".equalsIgnoreCase(sortDir)) {
                comparator = comparator.reversed();
            }
            tickets.sort(comparator);
        }

        return tickets;
    }
}
