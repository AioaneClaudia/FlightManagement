package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InFileRepository<String, Ticket> {
    public TicketRepository() {
        super("src/main/resources/data/Ticket.json", Ticket.class);
    }
}