package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    // NEW: filtering + sorting
    public List<Airplane> getFilteredAndSortedAirplanes(
            String model,
            Integer capacityMin,
            Integer capacityMax,
            String sortField,
            String sortDir) {

        List<Airplane> list = airplaneRepository.findAll();

        // FILTERING
        if (model != null && !model.isEmpty()) {
            String lower = model.toLowerCase();
            list = list.stream()
                    .filter(a -> a.getModel() != null &&
                            a.getModel().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        if (capacityMin != null) {
            list = list.stream()
                    .filter(a -> a.getCapacity() >= capacityMin)
                    .collect(Collectors.toList());
        }

        if (capacityMax != null) {
            list = list.stream()
                    .filter(a -> a.getCapacity() <= capacityMax)
                    .collect(Collectors.toList());
        }

        // SORTING
        Comparator<Airplane> comparator;

        switch (sortField) {
            case "number":
                comparator = Comparator.comparing(Airplane::getNumber);
                break;
            case "model":
                comparator = Comparator.comparing(Airplane::getModel);
                break;
            case "capacity":
                comparator = Comparator.comparing(Airplane::getCapacity);
                break;
            default:
                comparator = Comparator.comparing(Airplane::getId);
                break;
        }

        if ("desc".equals(sortDir)) {
            comparator = comparator.reversed();
        }

        list = list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return list;
    }
}
