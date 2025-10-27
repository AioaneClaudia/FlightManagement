package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirportEmployeeController {

    @GetMapping("/airportemployee/test")
    @ResponseBody
    public String testAirportEmployeeController() {
        return "AirportEmployeeController lauft!";
    }
}
