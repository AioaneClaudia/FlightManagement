package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.Luggage;
import org.springframework.stereotype.Repository;

@Repository
public class LuggageRepository extends InFileRepository<String, Luggage> {
    public LuggageRepository() {
        super("src/main/resources/data/Luggage.json", Luggage.class);
    }
}