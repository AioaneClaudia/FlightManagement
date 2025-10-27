package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StaffController {

    @GetMapping("/staff/test")
    @ResponseBody
    public String testStaffController() {
        return "StaffController lauft!";
    }
}
