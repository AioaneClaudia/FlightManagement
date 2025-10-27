package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirplaneController {

    @GetMapping("/airplane/test")
    @ResponseBody
    public String testAirplaneController() {
        return "AirplaneController lauft!";
    }
}
