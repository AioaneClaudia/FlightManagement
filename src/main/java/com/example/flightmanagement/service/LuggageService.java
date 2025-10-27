package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Luggage;
import com.example.flightmanagement.repository.LuggageRepository;
import java.util.List;

public class LuggageService {
    private LuggageRepository luggageRepository = new LuggageRepository();

    public void addLuggage(Luggage luggage) {
        luggageRepository.save(luggage);
    }

    public List<Luggage> getAllLuggages() {
        return luggageRepository.findAll();
    }

    public Luggage getLuggageById(String id) {
        return luggageRepository.findById(id);
    }

    public void removeLuggage(String id) {
        luggageRepository.delete(id);
    }

    public void updateLuggageStatus(String id, String newStatus) {
        Luggage luggage = luggageRepository.findById(id);
        if (luggage != null) {
            luggage.setStatus(newStatus);
        }
    }
}
