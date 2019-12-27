package com.netcracker.edu.group6.T;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class GreetingController {
    @GetMapping("/test/test")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model
    ) {
        model.addAttribute("name", name);

        LocalDate date = LocalDate.now(); // получаем текущую дату
        model.addAttribute("date", date);

        return "test";
    }

}