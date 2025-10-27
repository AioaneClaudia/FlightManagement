package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LuggageController {

    @GetMapping("/luggage/test")
    @ResponseBody
    public String testLuggageController() {
        return "LuggageController lauft!";
    }
}
