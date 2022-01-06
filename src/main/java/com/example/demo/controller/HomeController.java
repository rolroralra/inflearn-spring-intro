package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/hello")
    String hello(Model model) {
        model.addAttribute("data", "SpringBoot");
        return "hello";
    }
}
