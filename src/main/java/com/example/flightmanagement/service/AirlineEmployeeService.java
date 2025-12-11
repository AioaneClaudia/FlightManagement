package com.example.flightmanagement.service;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.repository.AirlineEmployeeRepository;
import org.springframework.data.domain.Sort;
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
    public List<AirlineEmployee> getFilteredAndSortedEmployees(String name,
                                                               String role,
                                                               String sortField,
                                                               String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        List<AirlineEmployee> list = employeeRepository.findAll(sort);

        // filtrări simple în memorie (suficient pentru proiect)
        if (name != null && !name.isBlank()) {
            list = list.stream()
                    .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        if (role != null && !role.isBlank()) {
            list = list.stream()
                    .filter(e -> e.getRole().toLowerCase().contains(role.toLowerCase()))
                    .toList();
        }

        return list;
    }

}
