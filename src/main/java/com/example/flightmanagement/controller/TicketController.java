package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TicketController {

    @GetMapping("/ticket/test")
    @ResponseBody
    public String testTicketController() {
        return "TicketController lauft!";
    }
}
