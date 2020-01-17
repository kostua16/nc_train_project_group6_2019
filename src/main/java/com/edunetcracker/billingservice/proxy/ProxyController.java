package com.edunetcracker.billingservice.proxy;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProxyController {
    @GetMapping("/hello/{name}")
    public String getHello(@PathVariable String name) {
        String result = Variables.HelloText + " " + name;
        return result;
    }
    @PostMapping("/hello/{name}")
    public void putHello(@PathVariable String name) {
        Variables.HelloText = name;
    }
}
