package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Ticket;

public class TicketRepository extends InMemoryRepository<String, Ticket> {

    public TicketRepository() {
        super();
    }
    // poti adauga metode specifice dacă este nevoie
}