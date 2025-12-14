package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Airplane;
import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.service.AirplaneService;
import com.example.flightmanagement.service.FlightService;
import com.example.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final NoticeBoardService noticeBoardService;
    private final AirplaneService airplaneService;

    public FlightController(FlightService flightService,
                            NoticeBoardService noticeBoardService,
                            AirplaneService airplaneService) {
        this.flightService = flightService;
        this.noticeBoardService = noticeBoardService;
        this.airplaneService = airplaneService;
    }

//    @GetMapping
//    public String getAllFlights(Model model, @ModelAttribute("message") String message) {
//        model.addAttribute("flights", flightService.getAllFlights());
//        if (message != null && !message.isBlank()) {
//            model.addAttribute("message", message);
//        }
//        return "flight/index";
//    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String noticeBoard,
            @RequestParam(required = false) String airplane,
            @RequestParam(required = false) String departureFrom,
            @RequestParam(required = false) String departureTo,
            @RequestParam(required = false) String arrivalFrom,
            @RequestParam(required = false) String arrivalTo,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("message") String message
    ) {
        List<Flight> flights = flightService.getFilteredAndSorted(
                id, name, noticeBoard, airplane,
                departureFrom, departureTo, arrivalFrom, arrivalTo,
                sortField, sortDir
        );

        model.addAttribute("flights", flights);

        // păstrează valorile filtrelor
        model.addAttribute("idFilter", id);
        model.addAttribute("nameFilter", name);
        model.addAttribute("noticeBoardFilter", noticeBoard);
        model.addAttribute("airplaneFilter", airplane);
        model.addAttribute("departureFrom", departureFrom);
        model.addAttribute("departureTo", departureTo);
        model.addAttribute("arrivalFrom", arrivalFrom);
        model.addAttribute("arrivalTo", arrivalTo);

        // sortare
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        if (message != null && !message.isBlank()) {
            model.addAttribute("message", message);
        }

        return "flight/index";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("flight", new Flight());
        return "flight/form";
    }

    @PostMapping
    public String addFlight(@Valid @ModelAttribute("flight") Flight flight,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {

        flightService.validateAndSaveFlight(flight, result);

        if (result.hasErrors()) {
            return "flight/form";
        }

        redirectAttributes.addFlashAttribute("message", "Flight saved successfully");
        return "redirect:/flights";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) return "redirect:/flights";

        model.addAttribute("flight", flight);
        return "flight/form";
    }

    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable String id,
                               @Valid @ModelAttribute("flight") Flight flight,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        Flight existing = flightService.getFlightById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("message", "Flight not found");
            return "redirect:/flights";
        }

        flight.setId(id);

        flightService.validateAndSaveFlight(flight, result);

        if (result.hasErrors()) {
            return "flight/form";
        }

        redirectAttributes.addFlashAttribute("message", "Flight updated successfully");
        return "redirect:/flights";
    }


    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable String id,
                               RedirectAttributes redirectAttributes) {
        Flight f = flightService.getFlightById(id);
        if (f == null) {
            redirectAttributes.addFlashAttribute("message", "Flight not found");
            return "redirect:/flights";
        }

        flightService.removeFlight(id);
        redirectAttributes.addFlashAttribute("message", "Flight deleted");
        return "redirect:/flights";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) return "redirect:/flights";

        model.addAttribute("flight", flight);
        return "flight/details";
    }

}