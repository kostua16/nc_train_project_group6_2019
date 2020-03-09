package com.examplenetcracker.billingservice.atc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting") // для адресной строки
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
            model.addAttribute("name", name);
        return "greeting"; // возвращает html файлу с данным название
    }

}//автоваер на конструкторе