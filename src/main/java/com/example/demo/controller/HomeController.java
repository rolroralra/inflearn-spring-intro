package com.example.demo.controller;

import com.example.demo.controller.dto.HelloDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("name", "Guest");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping(value = "hello-api")
    @ResponseBody
    public HelloDto helloApi(@RequestParam(defaultValue = "Guest") String name) {
        return new HelloDto(name);
    }
}
