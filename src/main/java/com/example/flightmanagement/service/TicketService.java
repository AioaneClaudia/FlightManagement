package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.model.Ticket;
import com.example.flightmanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public TicketService(TicketRepository ticketRepository, FlightService flightService, PassengerService passengerService) {
        this.ticketRepository = ticketRepository;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void cancelTicket(String id) {
        ticketRepository.deleteById(id);
    }

    /**
     * Validare și setare Flight și Passenger pe Ticket
     * Aruncă IllegalArgumentException dacă oricare nu există sau nu e setat
     */
    public void validateAndSetRelations(Ticket ticket) {
        // Flight
        if (ticket.getFlightId() == null || ticket.getFlightId().isBlank()) {
            throw new IllegalArgumentException("Flight is required");
        }
        Flight flight = flightService.getFlightById(ticket.getFlightId());
        if (flight == null) {
            throw new IllegalArgumentException("Selected flight does not exist");
        }
        ticket.setFlight(flight);

        // Passenger
        if (ticket.getPassengerId() == null || ticket.getPassengerId().isBlank()) {
            throw new IllegalArgumentException("Passenger is required");
        }
        Passenger passenger = passengerService.getPassengerById(ticket.getPassengerId());
        if (passenger == null) {
            throw new IllegalArgumentException("Selected passenger does not exist");
        }
        ticket.setPassenger(passenger);
    }

    public void issueTicket(Ticket ticket) {
        validateAndSetRelations(ticket);
        ticketRepository.save(ticket);
    }

    public void updateTicket(String id, Ticket ticket) {
        ticket.setId(id);
        validateAndSetRelations(ticket);
        ticketRepository.save(ticket);
    }
}
