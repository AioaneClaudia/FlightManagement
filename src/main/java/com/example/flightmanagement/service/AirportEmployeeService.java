package com.example.flightmanagement.service;

import com.example.flightmanagement.model.AirportEmployee;
import com.example.flightmanagement.repository.AirportEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportEmployeeService {

    private final AirportEmployeeRepository repository;

    public AirportEmployeeService(AirportEmployeeRepository repository) {
        this.repository = repository;
    }

    public void addEmployee(AirportEmployee employee) {
        if (repository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Employee with this ID already exists");
        }
        repository.save(employee);
    }

    public void updateEmployee(AirportEmployee employee) {
        repository.save(employee); // suprascrie sau update
    }

    public List<AirportEmployee> getAllEmployees() {
        return repository.findAll();
    }

    public AirportEmployee getEmployeeById(String id) {
        Optional<AirportEmployee> emp = repository.findById(id);
        return emp.orElse(null);
    }

    public void removeEmployee(String id) {
        repository.deleteById(id);
    }
}
