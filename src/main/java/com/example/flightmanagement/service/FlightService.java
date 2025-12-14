package com.example.flightmanagement.service;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final NoticeBoardService noticeBoardService;
    private final AirplaneService airplaneService;

    public FlightService(FlightRepository flightRepository,
                         NoticeBoardService noticeBoardService,
                         AirplaneService airplaneService) {
        this.flightRepository = flightRepository;
        this.noticeBoardService = noticeBoardService;
        this.airplaneService = airplaneService;
    }

    // ============================
    // BASIC CRUD
    // ============================

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

    // ============================
    // BUSINESS VALIDATION + SAVE
    // ============================

    public void validateAndSaveFlight(Flight flight, BindingResult result) {

        // -------- NoticeBoard validation --------
        String nbId = (flight.getNoticeBoard() != null)
                ? flight.getNoticeBoard().getId()
                : null;

        if (nbId == null || nbId.isBlank()) {
            result.rejectValue(
                    "noticeBoard",
                    "NotNull",
                    "NoticeBoard ID is required"
            );
        } else {
            NoticeBoard nb = noticeBoardService.getNoticeBoardById(nbId);
            if (nb == null) {
                result.rejectValue(
                        "noticeBoard",
                        "NotFound",
                        "NoticeBoard does not exist"
                );
            } else {
                flight.setNoticeBoard(nb); // setăm obiectul REAL
            }
        }

        // -------- Airplane validation --------
        String apId = (flight.getAirplane() != null)
                ? flight.getAirplane().getId()
                : null;

        if (apId == null || apId.isBlank()) {
            result.rejectValue(
                    "airplane",
                    "NotNull",
                    "Airplane ID is required"
            );
        } else {
            Airplane ap = airplaneService.getAirplaneById(apId);
            if (ap == null) {
                result.rejectValue(
                        "airplane",
                        "NotFound",
                        "Airplane does not exist"
                );
            } else {
                flight.setAirplane(ap); // setăm obiectul REAL
            }
        }

        // dacă există erori → NU salvăm
        if (result.hasErrors()) {
            return;
        }

        flightRepository.save(flight);
    }

    // ============================
    // FILTER + SORT
    // ============================

    public List<Flight> getFilteredAndSorted(
            String idFilter,
            String nameFilter,
            String noticeBoardFilter,
            String airplaneFilter,
            String departureFrom,
            String departureTo,
            String arrivalFrom,
            String arrivalTo,
            String sortField,
            String sortDir
    ) {

        List<Flight> list = flightRepository.findAll();

        // ----- ID filter -----
        if (idFilter != null && !idFilter.isBlank()) {
            String lower = idFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getId() != null &&
                            f.getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // ----- Name filter -----
        if (nameFilter != null && !nameFilter.isBlank()) {
            String lower = nameFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getName() != null &&
                            f.getName().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // ----- NoticeBoard filter -----
        if (noticeBoardFilter != null && !noticeBoardFilter.isBlank()) {
            String lower = noticeBoardFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getNoticeBoard() != null &&
                            f.getNoticeBoard().getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // ----- Airplane filter -----
        if (airplaneFilter != null && !airplaneFilter.isBlank()) {
            String lower = airplaneFilter.toLowerCase();
            list = list.stream()
                    .filter(f -> f.getAirplane() != null &&
                            f.getAirplane().getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // ----- Departure time -----
        if (departureFrom != null && !departureFrom.isBlank()) {
            LocalDateTime from = LocalDateTime.parse(departureFrom);
            list = list.stream()
                    .filter(f -> f.getDepartureTime() != null &&
                            !f.getDepartureTime().isBefore(from))
                    .collect(Collectors.toList());
        }

        if (departureTo != null && !departureTo.isBlank()) {
            LocalDateTime to = LocalDateTime.parse(departureTo);
            list = list.stream()
                    .filter(f -> f.getDepartureTime() != null &&
                            !f.getDepartureTime().isAfter(to))
                    .collect(Collectors.toList());
        }

        // ----- Arrival time -----
        if (arrivalFrom != null && !arrivalFrom.isBlank()) {
            LocalDateTime from = LocalDateTime.parse(arrivalFrom);
            list = list.stream()
                    .filter(f -> f.getArrivalTime() != null &&
                            !f.getArrivalTime().isBefore(from))
                    .collect(Collectors.toList());
        }

        if (arrivalTo != null && !arrivalTo.isBlank()) {
            LocalDateTime to = LocalDateTime.parse(arrivalTo);
            list = list.stream()
                    .filter(f -> f.getArrivalTime() != null &&
                            !f.getArrivalTime().isAfter(to))
                    .collect(Collectors.toList());
        }

        // ----- Sorting -----
        Comparator<Flight> comparator;

        switch (sortField) {
            case "name":
                comparator = Comparator.comparing(Flight::getName);
                break;
            case "noticeBoard":
                comparator = Comparator.comparing(f -> f.getNoticeBoard().getId());
                break;
            case "airplane":
                comparator = Comparator.comparing(f -> f.getAirplane().getId());
                break;
            case "departureTime":
                comparator = Comparator.comparing(Flight::getDepartureTime);
                break;
            case "arrivalTime":
                comparator = Comparator.comparing(Flight::getArrivalTime);
                break;
            default:
                comparator = Comparator.comparing(Flight::getId);
        }

        if ("desc".equals(sortDir)) {
            comparator = comparator.reversed();
        }

        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
