package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.repository.AirplaneRepository;
import java.util.List;

public class AirplaneService {
    private AirplaneRepository airplaneRepository = new AirplaneRepository();

    public void addAirplane(Airplane airplane) {
        airplaneRepository.save(airplane);
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(String id) {
        return airplaneRepository.findById(id);
    }

    public void removeAirplane(String id) {
        airplaneRepository.delete(id);
    }
}
