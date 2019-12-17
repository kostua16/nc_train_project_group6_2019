package com.netcracker.edu.group6;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Control {
    @RequestMapping("/")
    public String home() {
        return "Change to localhost:8080/test1/test1";
    }

    @RequestMapping("/test1/test1")
    public String test1_test1() {
        return ReadTextFile.read();
    }
}

