package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Luggage;
import java.util.*;

public class LuggageRepository {
    private Map<String, Luggage> luggages = new HashMap<>();

    public void save(Luggage luggage) {
        luggages.put(luggage.getId(), luggage);
    }

    public List<Luggage> findAll() {
        return new ArrayList<>(luggages.values());
    }

    public Luggage findById(String id) {
        return luggages.get(id);
    }

    public void delete(String id) {
        luggages.remove(id);
    }
}
