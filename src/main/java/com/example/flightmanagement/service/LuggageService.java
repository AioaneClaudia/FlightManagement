package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.model.LuggageStatus;
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

    // Ticket-ul deja este setat în controller
    public void add(Luggage luggage) {
        repo.save(luggage);
    }

    public void update(String id, Luggage updated) {
        Luggage luggage = getById(id);
        if (luggage != null) {
            luggage.setType(updated.getType());
            luggage.setWeight(updated.getWeight());

            // Dacă ticket-ul a fost schimbat
            if (updated.getTicket() != null) {
                luggage.setTicket(updated.getTicket());
            }

            repo.save(luggage);
        }
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
