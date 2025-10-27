package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Airplane;
import java.util.*;

public class AirplaneRepository {
    private Map<String, Airplane> airplanes = new HashMap<>();

    public void save(Airplane airplane) {
        airplanes.put(airplane.getId(), airplane);
    }

    public List<Airplane> findAll() {
        return new ArrayList<>(airplanes.values());
    }

    public Airplane findById(String id) {
        return airplanes.get(id);
    }

    public void delete(String id) {
        airplanes.remove(id);
    }
}
