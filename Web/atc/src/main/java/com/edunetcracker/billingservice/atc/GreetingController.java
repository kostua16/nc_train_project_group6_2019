package com.edunetcracker.billingservice.atc;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class GreetingController {

    @GetMapping("/greeting") // для адресной строки
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        String url = ""; //локал хост и тд
        ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class);
//      if (responseAccount.getBody())  //

        model.addAttribute("name", name);
        return "greeting"; // возвращает html файлу с данным название
    }




}//автоваер на конструкторе

