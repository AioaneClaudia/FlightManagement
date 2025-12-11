package com.example.flightmanagement.config;

import com.example.flightmanagement.model.*;
import com.example.flightmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final FlightAssignmentRepository assignmentRepository;
    private final LuggageRepository luggageRepository;
    private final PassengerRepository passengerRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final AirplaneRepository airplaneRepository;
    private final AirlineEmployeeRepository airlineEmployeeRepository;
    private final AirportEmployeeRepository airportEmployeeRepository;

    public DataInitializer(FlightRepository flightRepository,
                           TicketRepository ticketRepository,
                           FlightAssignmentRepository assignmentRepository,
                           LuggageRepository luggageRepository,
                           PassengerRepository passengerRepository,
                           NoticeBoardRepository noticeBoardRepository,
                           AirplaneRepository airplaneRepository,
                           AirlineEmployeeRepository airlineEmployeeRepository,
                           AirportEmployeeRepository airportEmployeeRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
        this.assignmentRepository = assignmentRepository;
        this.luggageRepository = luggageRepository;
        this.passengerRepository = passengerRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.airplaneRepository = airplaneRepository;
        this.airlineEmployeeRepository = airlineEmployeeRepository;
        this.airportEmployeeRepository = airportEmployeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        // ======================
        // 1️ Passengers
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
        // 2️ NoticeBoards
        // ======================
        if (noticeBoardRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                NoticeBoard nb = new NoticeBoard("NB" + i, LocalDate.now().plusDays(i).toString());
                noticeBoardRepository.save(nb);
            }
        }

        // ======================
        // 3️ Airplanes
        // ======================
        if (airplaneRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Airplane a = new Airplane();
                a.setId("A" + i);
                a.setNumber(100 + i);
                a.setModel("Model " + i);
                a.setCapacity(150 + i * 10);
                airplaneRepository.save(a);
            }
        }

        List<Passenger> passengers = passengerRepository.findAll();
        List<NoticeBoard> noticeBoards = noticeBoardRepository.findAll();
        List<Airplane> airplanes = airplaneRepository.findAll();

        // ======================
        // 4️ Flights
        // ======================
        if (flightRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Flight f = new Flight();
                f.setId("F" + i);
                f.setName("Flight " + i);
                f.setNoticeBoard(noticeBoards.get(random.nextInt(noticeBoards.size())));
                f.setAirplane(airplanes.get(random.nextInt(airplanes.size())));
                f.setDepartureTime(LocalDateTime.now().plusDays(random.nextInt(30)).truncatedTo(ChronoUnit.MINUTES));
                f.setArrivalTime(f.getDepartureTime().plusHours(2 + random.nextInt(5)));
                flightRepository.save(f);
            }
        }

        List<Flight> flights = flightRepository.findAll();

        // ======================
        // 5 Tickets & Luggage
        // ======================
        if (ticketRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Flight f = flights.get(random.nextInt(flights.size()));
                Passenger p = passengers.get(random.nextInt(passengers.size()));

                Ticket t = new Ticket();
                t.setPassengerName(p.getName());
                t.setPassengerId(p.getId());
                t.setSeatNumber("S" + (i + 1));
                t.setPrice(100.0 + random.nextInt(400));
                t.setFlight(f);
                t.setPassenger(p);

                ticketRepository.save(t);

                Random random1 = new Random();
                LuggageStatus[] statuses = LuggageStatus.values();

                for (int j = 1; j <= 2; j++) {
                    Luggage l = new Luggage();
                    l.setType(j == 1 ? "Cabin" : "Hold");
                    l.setWeight(5 + random1.nextInt(20));
                    l.setTicket(t);

                    // Setăm status aleator
                    LuggageStatus randomStatus = statuses[random1.nextInt(statuses.length)];
                    l.setStatus(randomStatus);

                    luggageRepository.save(l);
                }
            }
        }

        // ======================
        // 6️ FlightAssignments
        // ======================
        if (assignmentRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                Flight f = flights.get(random.nextInt(flights.size()));
                FlightAssignment fa = new FlightAssignment();
                fa.setId("FA" + i);
                fa.setFlight(f);
                fa.setStaffId("Staff" + i);
                assignmentRepository.save(fa);
            }
        }

        // ======================
        // 7️ AirlineEmployees
        // ======================
        if (airlineEmployeeRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                AirlineEmployee ae = new AirlineEmployee();
                ae.setId("AE" + i);
                ae.setName("Employee " + i);
                ae.setRole(i % 2 == 0 ? "Pilot" : "Crew");
                airlineEmployeeRepository.save(ae);
            }
        }

        // ======================
        // 8️ AirportEmployees
        // ======================
        if (airportEmployeeRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                AirportEmployee ae = new AirportEmployee();
                ae.setId("AP" + i);
                ae.setName("AirportEmployee " + i);
                ae.setDesignation("Designation " + i);
                ae.setDepartment("Department " + ((i % 3) + 1));
                airportEmployeeRepository.save(ae);
            }
        }

        System.out.println(" Data initialization complete!");
    }
}
