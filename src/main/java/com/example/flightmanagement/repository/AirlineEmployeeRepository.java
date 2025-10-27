package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.AirlineEmployee;
import java.util.*;

public class AirlineEmployeeRepository {
    private Map<String, AirlineEmployee> employees = new HashMap<>();

    public void save(AirlineEmployee employee) {
        employees.put(employee.getId(), employee);
    }

    public List<AirlineEmployee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public AirlineEmployee findById(String id) {
        return employees.get(id);
    }

    public void delete(String id) {
        employees.remove(id);
    }
}
