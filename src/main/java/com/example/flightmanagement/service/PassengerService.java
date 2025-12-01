package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // Creare pasager nou
    public Passenger createPassenger(Passenger passenger) {
        passenger.setId(null); // ne asigurăm că ID-ul nu e setat
        return passengerRepository.save(passenger);
    }

    // Actualizare pasager existent
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
