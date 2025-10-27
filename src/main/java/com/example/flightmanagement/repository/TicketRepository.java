package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Ticket;
import java.util.*;

public class TicketRepository {
    private Map<String, Ticket> tickets = new HashMap<>();

    public void save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets.values());
    }

    public Ticket findById(String id) {
        return tickets.get(id);
    }

    public void delete(String id) {
        tickets.remove(id);
    }
}
