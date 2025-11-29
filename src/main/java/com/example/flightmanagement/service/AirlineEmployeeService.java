package com.example.flightmanagement.service;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.repository.AirlineEmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AirlineEmployeeService {

    private final AirlineEmployeeRepository employeeRepository;

    public AirlineEmployeeService(AirlineEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public AirlineEmployee addEmployee(AirlineEmployee employee) {
        return employeeRepository.save(employee);
    }

    public List<AirlineEmployee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public AirlineEmployee getEmployeeById(String id) {
        Optional<AirlineEmployee> opt = employeeRepository.findById(id);
        return opt.orElse(null);
    }

    public void removeEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
