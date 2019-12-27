package com.netcracker.edu.group6;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DataController {

    @RequestMapping("/test/test")//все методы будут за ним
    public LocalDate greeting() {
        LocalDate date = LocalDate.now(); // получаем текущую дату
        return date;
    }
}