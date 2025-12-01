package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    // Optional: metode suplimentare, ex: findByEmail, findByName
}
