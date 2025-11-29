package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.AirlineEmployee;
import com.example.flightmanagement.model.Staff;
import com.example.flightmanagement.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffs", staffService.getAllStaff());
        return "staff/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("staff", new AirlineEmployee());
        return "staff/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("staff") Staff staff, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "staff/form";
        }
        staffService.addStaff(staff);
        return "redirect:/staffs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        staffService.removeStaff(id);
        return "redirect:/staffs";
    }
}
