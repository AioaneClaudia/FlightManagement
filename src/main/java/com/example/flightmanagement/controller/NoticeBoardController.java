package com.example.flightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NoticeBoardController {

    @GetMapping("/noticeboard/test")
    @ResponseBody
    public String testNoticeBoardController() {
        return "NoticeBoardController lauft!";
    }
}
