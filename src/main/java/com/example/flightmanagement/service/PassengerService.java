package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getFilteredPassengers(
            String currency,
            LocalDate dateFrom,
            LocalDate dateTo,
            String sortField,
            String sortDir) {

        // 1️⃣ Preluăm toți pasagerii
        List<Passenger> passengers = new ArrayList<>(passengerRepository.findAll());

        // 2️⃣ Filtrare după currency
        if (currency != null && !currency.isEmpty()) {
            passengers = passengers.stream()
                    .filter(p -> p.getCurrency().equalsIgnoreCase(currency))
                    .collect(Collectors.toList()); // listă mutabilă
        }

        // 3️⃣ Filtrare după interval de date
        if (dateFrom != null) {
            passengers = passengers.stream()
                    .filter(p -> !p.getDateOfBirth().isBefore(dateFrom))
                    .collect(Collectors.toList());
        }
        if (dateTo != null) {
            passengers = passengers.stream()
                    .filter(p -> !p.getDateOfBirth().isAfter(dateTo))
                    .collect(Collectors.toList());
        }

        // 4️⃣ Sortare după câmpul specificat
        Comparator<Passenger> comparator;
        switch (sortField) {
            case "currency" -> comparator = Comparator.comparing(Passenger::getCurrency, String.CASE_INSENSITIVE_ORDER);
            case "email" -> comparator = Comparator.comparing(Passenger::getEmail, String.CASE_INSENSITIVE_ORDER);
            case "dateOfBirth" -> comparator = Comparator.comparing(Passenger::getDateOfBirth);
            default -> comparator = Comparator.comparing(Passenger::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        // 5️⃣ Aplicăm sortarea pe listă
        passengers.sort(comparator);

        return passengers;
    }



    // celelalte metode rămân neschimbate
    public Passenger createPassenger(Passenger passenger) {
        passenger.setId(null);
        return passengerRepository.save(passenger);
    }

    public Passenger updatePassenger(String id, Passenger passenger) {
        Passenger existing = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        existing.setName(passenger.getName());
        existing.setEmail(passenger.getEmail());
        existing.setCurrency(passenger.getCurrency());
        existing.setDateOfBirth(passenger.getDateOfBirth());
        return passengerRepository.save(existing);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(String id) {
        return passengerRepository.findById(id).orElse(null);
    }

    public void removePassenger(String id) {
        passengerRepository.deleteById(id);
    }
}

