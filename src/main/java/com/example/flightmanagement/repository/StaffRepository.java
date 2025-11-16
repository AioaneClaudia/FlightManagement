package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepository extends InFileRepository<String, Staff> {
    public StaffRepository() {
        super("src/main/resources/data/Staff.json", Staff.class);
    }
}