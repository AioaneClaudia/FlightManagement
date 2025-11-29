package com.example.flightmanagement.config;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.model.AirportEmployee;
import com.example.flightmanagement.repository.AirlineEmployeeRepository;
import com.example.flightmanagement.repository.AirportEmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AirlineEmployeeRepository airlineEmployeeRepository;
    private final AirportEmployeeRepository airportEmployeeRepository;

    public DataInitializer(AirlineEmployeeRepository airlineEmployeeRepository,
                           AirportEmployeeRepository airportEmployeeRepository) {
        this.airlineEmployeeRepository = airlineEmployeeRepository;
        this.airportEmployeeRepository = airportEmployeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // --- AirlineEmployee initialisieren ---
        if (airlineEmployeeRepository.count() < 10) {
            for (int i = 1; i <= 10; i++) {
                AirlineEmployee e = new AirlineEmployee();
                e.setName("Employee " + i);
                e.setRole(i % 3 == 0 ? "Pilot" : (i % 3 == 1 ? "Crew" : "Dispatcher"));
                airlineEmployeeRepository.save(e);
            }
        }

        // --- AirportEmployee initialisieren ---
        if (airportEmployeeRepository.count() < 10) {
            for (int i = 1; i <= 10; i++) {
                AirportEmployee e = new AirportEmployee();
                e.setId("AEP" + i); // ID manual, deoarece la AirportEmployee folosim String ID
                e.setName("Airport Employee " + i);
                e.setDesignation(i % 2 == 0 ? "Manager" : "Staff");
                e.setDepartment(i % 3 == 0 ? "Operations" : (i % 3 == 1 ? "Security" : "Logistics"));
                airportEmployeeRepository.save(e);
            }
        }
    }
}
