package com.example.flightmanagement.config;

import com.example.flightmanagement.model.*;
import com.example.flightmanagement.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final FlightAssignmentRepository assignmentRepository;
    private final LuggageRepository luggageRepository;
    private final PassengerRepository passengerRepository;

    public DataInitializer(FlightRepository flightRepository,
                           TicketRepository ticketRepository,
                           FlightAssignmentRepository assignmentRepository,
                           LuggageRepository luggageRepository,
                           PassengerRepository passengerRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.assignmentRepository = assignmentRepository;
        this.luggageRepository = luggageRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // ======================
        // 1️⃣ Passengers
        // ======================
        if (passengerRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Passenger p = new Passenger();
                p.setName("Passenger " + i);
                p.setEmail("passenger" + i + "@example.com");
                p.setCurrency("USD");
                p.setDateOfBirth(LocalDate.now().minusYears(20 + i));
                passengerRepository.save(p);
            }
        }

        // ======================
        // 2️⃣ Flights
        // ======================
        if (flightRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Flight f = new Flight("F" + i, "Flight " + i, "NB" + i, "AP" + i);
                f.setDepartureTime(LocalDateTime.now().plusDays(i));
                f.setArrivalTime(LocalDateTime.now().plusDays(i).plusHours(2));
                flightRepository.save(f);
            }
        }

        // ======================
        // 3️⃣ Flight Assignments
        // ======================
        if (assignmentRepository.count() == 0) {
            int i = 1;
            for (Flight f : flightRepository.findAll()) {
                FlightAssignment fa = new FlightAssignment("A" + i, f, "S" + i);
                assignmentRepository.save(fa);
                i++;
                if (i > 10) break;
            }
        }

        // ======================
        // 4️⃣ Tickets
        // ======================
        if (ticketRepository.count() == 0) {
            int i = 1;
            List<Passenger> passengers = passengerRepository.findAll();
            for (Flight f : flightRepository.findAll()) {
                Ticket t = new Ticket();

                Passenger p = passengers.get((i - 1) % passengers.size()); // alegem un pasager

                t.setPassengerId(p.getId());
                t.setPassenger(p);   // ADĂUGAT - stabilește relația JPA reală


                t.setPassengerName(p.getName());
                t.setPrice(100.0 + i);
                t.setFlight(f);
                t.setSeatNumber("SE" + i);
                ticketRepository.save(t);

                // ======================
                // 5️⃣ Luggages for each ticket
                // ======================
                int numLuggages = 1 + new Random().nextInt(3); // 1-3 luggages
                for (int j = 1; j <= numLuggages; j++) {
                    Luggage l = new Luggage();
                    l.setType(j % 2 == 0 ? "Cabin" : "Hold");
                    l.setWeight(5 + new Random().nextInt(15)); // 5-20 kg
                    l.setTicket(t);
                    luggageRepository.save(l);
                }

                i++;
                if (i > 10) break;
            }
        }
    }
}
