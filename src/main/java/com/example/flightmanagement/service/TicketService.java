package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.TicketRepository;
import java.util.List;

public class TicketService {
    private TicketRepository ticketRepository = new TicketRepository();

    public void issueTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id);
    }

    public void cancelTicket(String id) {
        ticketRepository.delete(id);
    }

    // Beispiel-Logik: Preisänderung
    public void updateTicketPrice(String id, double newPrice) {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket != null) {
            ticketRepository.save(new Ticket(
                    ticket.getId(),
                    ticket.getPassengerId(),
                    ticket.getFlightId(),
                    newPrice,
                    ticket.getSeatNumber()
            ));
        }
    }
}
