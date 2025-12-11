package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void addFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    public void removeFlight(String id) {
        flightRepository.deleteById(id);
    }

    // Filtrare combinată
    public List<Flight> getFilteredAndSorted(
            String idFilter,
            String nameFilter,
            String noticeBoardFilter,
            String airplaneFilter,
            String departureFrom,    // ISO local datetime string: "yyyy-MM-dd'T'HH:mm"
            String departureTo,
            String arrivalFrom,
            String arrivalTo,
            String sortField,
            String sortDir
    ) {
        List<Flight> list = flightRepository.findAll();

        if (idFilter != null && !idFilter.isBlank()) {
            String lower = idFilter.toLowerCase();
            list = list.stream().filter(f -> f.getId() != null && f.getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        if (nameFilter != null && !nameFilter.isBlank()) {
            String lower = nameFilter.toLowerCase();
            list = list.stream().filter(f -> f.getName() != null && f.getName().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        if (noticeBoardFilter != null && !noticeBoardFilter.isBlank()) {
            String lower = noticeBoardFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getNoticeBoard() != null && f.getNoticeBoard().getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        if (airplaneFilter != null && !airplaneFilter.isBlank()) {
            String lower = airplaneFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getAirplane() != null && f.getAirplane().getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // Filtrare departures
        if (departureFrom != null && !departureFrom.isBlank()) {
            LocalDateTime from = LocalDateTime.parse(departureFrom);
            list = list.stream().filter(f -> f.getDepartureTime() != null && !f.getDepartureTime().isBefore(from))
                    .collect(Collectors.toList());
        }
        if (departureTo != null && !departureTo.isBlank()) {
            LocalDateTime to = LocalDateTime.parse(departureTo);
            list = list.stream().filter(f -> f.getDepartureTime() != null && !f.getDepartureTime().isAfter(to))
                    .collect(Collectors.toList());
        }

        // Filtrare arrivals
        if (arrivalFrom != null && !arrivalFrom.isBlank()) {
            LocalDateTime from = LocalDateTime.parse(arrivalFrom);
            list = list.stream().filter(f -> f.getArrivalTime() != null && !f.getArrivalTime().isBefore(from))
                    .collect(Collectors.toList());
        }
        if (arrivalTo != null && !arrivalTo.isBlank()) {
            LocalDateTime to = LocalDateTime.parse(arrivalTo);
            list = list.stream().filter(f -> f.getArrivalTime() != null && !f.getArrivalTime().isAfter(to))
                    .collect(Collectors.toList());
        }

        // Sortare
        Comparator<Flight> comp;
        switch (sortField) {
            case "name": comp = Comparator.comparing(Flight::getName); break;
            case "noticeBoard": comp = Comparator.comparing(f -> f.getNoticeBoard().getId()); break;
            case "airplane": comp = Comparator.comparing(f -> f.getAirplane().getId()); break;
            case "departureTime": comp = Comparator.comparing(Flight::getDepartureTime); break;
            case "arrivalTime": comp = Comparator.comparing(Flight::getArrivalTime); break;
            default: comp = Comparator.comparing(Flight::getId); break;
        }
        if ("desc".equals(sortDir)) comp = comp.reversed();

        return list.stream().sorted(comp).collect(Collectors.toList());
    }

}
