package com.example.flightmanagement.service;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.repository.AirlineEmployeeRepository;
import org.springframework.stereotype.Service;  // ← import lipsă

import java.util.List;

@Service
public class AirlineEmployeeService {

    private final AirlineEmployeeRepository employeeRepository;

    public AirlineEmployeeService(AirlineEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(AirlineEmployee employee) {
        employeeRepository.save(employee);
    }

    public List<AirlineEmployee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public AirlineEmployee getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public void removeEmployee(String id) {
        employeeRepository.delete(id);
    }
}
