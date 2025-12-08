package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Luggage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank(message = "Luggage type is required.")
    private String type; // ex: "Cabin", "Hold", etc.

    @NotNull(message = "Weight is required.")
    @Min(value = 1, message = "Weight must be at least 1 kg.")
    private Integer weight;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    @Transient
    private String ticketId;

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }


    // GETTERS & SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
}
