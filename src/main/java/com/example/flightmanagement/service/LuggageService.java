package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.LuggageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuggageService {

    private final LuggageRepository repo;
    private final TicketService ticketService;

    public LuggageService(LuggageRepository repo, TicketService ticketService) {
        this.repo = repo;
        this.ticketService = ticketService;
    }

    public List<Luggage> getAll() {
        return repo.findAll();
    }

    public Luggage getById(String id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Validare și setare ticket pe bagaj.
     * Aruncă RuntimeException dacă ticket-ul nu există sau nu e setat.
     */
    public void validateAndSetTicket(Luggage luggage) {
        if (luggage.getTicketId() == null || luggage.getTicketId().isBlank()) {
            throw new IllegalArgumentException("Ticket is required");
        }
        Ticket ticket = ticketService.getTicketById(luggage.getTicketId());
        if (ticket == null) {
            throw new IllegalArgumentException("Selected ticket does not exist");
        }
        luggage.setTicket(ticket);
    }

    public void add(Luggage luggage) {
        // Validare ticket înainte de salvare
        validateAndSetTicket(luggage);
        repo.save(luggage);
    }

    public void update(String id, Luggage updated) {
        Luggage luggage = getById(id);
        if (luggage != null) {
            luggage.setType(updated.getType());
            luggage.setWeight(updated.getWeight());

            // Validare și setare ticket dacă s-a schimbat
            if (updated.getTicketId() != null) {
                validateAndSetTicket(updated);
            }

            repo.save(luggage);
        }
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
