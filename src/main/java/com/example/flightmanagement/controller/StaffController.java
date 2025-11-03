package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.Staff;
import com.example.flightmanagement.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    private final StaffService staffService = new StaffService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffs", staffService.getAllStaff());
        return "staff/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        // folosim constructorul din Staff nu este posibil (abstract), dar pentru formular vom folosi o subclasă minimală
        model.addAttribute("staff", new com.example.flightmanagement.model.AirlineEmployee("", "", ""));
        return "staff/form";
    }

    @PostMapping
    public String create(@ModelAttribute Staff staff) {
        staffService.addStaff(staff);
        return "redirect:/staffs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        staffService.removeStaff(id);
        return "redirect:/staffs";
    }
}
