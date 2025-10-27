package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirlineEmployeeController {

    @GetMapping("/airlineemployee/test")
    @ResponseBody
    public String testAirlineEmployeeController() {
        return "AirlineEmployeeController lauft!";
    }
}
