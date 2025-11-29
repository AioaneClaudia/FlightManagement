package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    // Wenn du Staff-spezifische Queries brauchst, hier ergänzen
}
