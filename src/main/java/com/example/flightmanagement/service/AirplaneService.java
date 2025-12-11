package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public Airplane save(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    public void addAirplane(Airplane airplane) {
        airplaneRepository.save(airplane);
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(String id) {
        return airplaneRepository.findById(id).orElse(null);
    }

    public void removeAirplane(String id) {
        airplaneRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return airplaneRepository.existsById(id);
    }
}
