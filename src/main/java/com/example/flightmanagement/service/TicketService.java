package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
