package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirportEmployee;
import java.util.*;

public class AirportEmployeeRepository {
    private Map<String, AirportEmployee> employees = new HashMap<>();

    public void save(AirportEmployee employee) {
        employees.put(employee.getId(), employee);
    }

    public List<AirportEmployee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public AirportEmployee findById(String id) {
        return employees.get(id);
    }

    public void delete(String id) {
        employees.remove(id);
    }
}
