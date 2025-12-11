package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, String> {
    // poți adăuga query methods dacă ai nevoie (ex: findByNumber)
}
