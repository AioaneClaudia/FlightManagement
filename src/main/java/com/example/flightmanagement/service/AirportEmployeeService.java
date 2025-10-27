package com.example.flightmanagement.service;

import com.example.flightmanagement.model.AirportEmployee;
import com.example.flightmanagement.repository.AirportEmployeeRepository;
import java.util.List;

public class AirportEmployeeService {
    private AirportEmployeeRepository employeeRepository = new AirportEmployeeRepository();

    public void addEmployee(AirportEmployee employee) {
        employeeRepository.save(employee);
    }

    public List<AirportEmployee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public AirportEmployee getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public void removeEmployee(String id) {
        employeeRepository.delete(id);
    }
}
