package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Flight;
import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.service.FlightService;
import com.example.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final NoticeBoardService noticeBoardService;

    public FlightController(FlightService flightService, NoticeBoardService noticeBoardService) {
        this.flightService = flightService;
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    public String getAllFlights(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("flights", flightService.getAllFlights());
        if (message != null && !message.isBlank()) model.addAttribute("message", message);
        return "flight/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("boards", noticeBoardService.getAllNoticeBoards());
        return "flight/form";
    }

    @PostMapping
    public String addFlight(@Valid @ModelAttribute("flight") Flight flight,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        // always add boards to model (used if we return to form)
        model.addAttribute("boards", noticeBoardService.getAllNoticeBoards());

        // validate nested noticeBoard.id (binding from select th:field="*{noticeBoard.id}")
        String nbId = flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null;
        if (nbId == null || nbId.isBlank()) {
            result.rejectValue("noticeBoard", "NotNull", "NoticeBoard is required");
        } else {
            NoticeBoard nb = noticeBoardService.getNoticeBoardById(nbId);
            if (nb == null) {
                result.rejectValue("noticeBoard", "NotFound", "Selected NoticeBoard does not exist");
            } else {
                // attach managed entity
                flight.setNoticeBoard(nb);
            }
        }

        if (result.hasErrors()) {
            return "flight/form";
        }

        flightService.addFlight(flight);
        redirectAttributes.addFlashAttribute("message", "Flight saved successfully");
        return "redirect:/flights";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return "redirect:/flights";
        }
        model.addAttribute("flight", flight);
        model.addAttribute("boards", noticeBoardService.getAllNoticeBoards());
        return "flight/form";
    }

    @PostMapping("/{id}/edit")
    public String updateFlight(@PathVariable String id,
                               @Valid @ModelAttribute("flight") Flight flight,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        model.addAttribute("boards", noticeBoardService.getAllNoticeBoards());

        Flight existing = flightService.getFlightById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("message", "Flight not found");
            return "redirect:/flights";
        }

        // ensure path id is used
        flight.setId(id);

        // validate noticeBoard nested id
        String nbId = flight.getNoticeBoard() != null ? flight.getNoticeBoard().getId() : null;
        if (nbId == null || nbId.isBlank()) {
            result.rejectValue("noticeBoard", "NotNull", "NoticeBoard is required");
        } else {
            NoticeBoard nb = noticeBoardService.getNoticeBoardById(nbId);
            if (nb == null) {
                result.rejectValue("noticeBoard", "NotFound", "Selected NoticeBoard does not exist");
            } else {
                flight.setNoticeBoard(nb);
            }
        }

        if (result.hasErrors()) {
            return "flight/form";
        }

        flightService.addFlight(flight);
        redirectAttributes.addFlashAttribute("message", "Flight updated successfully");
        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Flight f = flightService.getFlightById(id);
        if (f == null) {
            redirectAttributes.addFlashAttribute("message", "Flight not found");
            return "redirect:/flights";
        }

        // You may want to check business rules (e.g., existing tickets/assignments)
        flightService.removeFlight(id);
        redirectAttributes.addFlashAttribute("message", "Flight deleted");
        return "redirect:/flights";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            return "redirect:/flights";
        }
        model.addAttribute("flight", flight);
        return "flight/details";
    }
}
