package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Passenger;
import com.example.flightmanagement.repository.PassengerRepository;
import java.util.List;

public class PassengerService {
    private PassengerRepository passengerRepository = new PassengerRepository();

    public void registerPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(String id) {
        return passengerRepository.findById(id);
    }

    public void removePassenger(String id) {
        passengerRepository.delete(id);
    }
}
