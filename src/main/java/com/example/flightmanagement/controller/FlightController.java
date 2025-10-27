package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlightController {

    @GetMapping("/flight/test")
    @ResponseBody
    public String testFlightController() {
        return "FlightController lauft!";
    }
}
