package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlightAssignmentController {

    @GetMapping("/flightassignment/test")
    @ResponseBody
    public String testFlightAssignmentController() {
        return "FlightAssignmentController lauft!";
    }
}
