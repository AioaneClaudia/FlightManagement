package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.model.LuggageStatus;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.LuggageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Luggage> getFilteredAndSorted(String type, String ticketId, LuggageStatus status,
                                              Integer weightFrom, Integer weightTo,
                                              String sortField, String sortDir) {
        return repo.findAll().stream()
                // Filtrare
                .filter(l -> type == null || type.isBlank() || l.getType().toLowerCase().contains(type.toLowerCase()))
                .filter(l -> ticketId == null || ticketId.isBlank() ||
                        (l.getTicket() != null && l.getTicket().getId().equals(ticketId)))
                .filter(l -> status == null || l.getStatus() == status)
                .filter(l -> weightFrom == null || l.getWeight() >= weightFrom)
                .filter(l -> weightTo == null || l.getWeight() <= weightTo)
                // Sortare
                .sorted((l1, l2) -> {
                    int cmp = 0;
                    if ("type".equals(sortField)) cmp = l1.getType().compareToIgnoreCase(l2.getType());
                    else if ("weight".equals(sortField)) cmp = l1.getWeight().compareTo(l2.getWeight());
                    else if ("ticket".equals(sortField)) {
                        String t1 = l1.getTicket() != null ? l1.getTicket().getId() : "";
                        String t2 = l2.getTicket() != null ? l2.getTicket().getId() : "";
                        cmp = t1.compareTo(t2);
                    }
                    else if ("status".equals(sortField)) cmp = l1.getStatus().compareTo(l2.getStatus());
                    else if ("id".equals(sortField)) cmp = l1.getId().compareTo(l2.getId());

                    return "desc".equals(sortDir) ? -cmp : cmp;
                })
                .collect(Collectors.toList());
    }

}
