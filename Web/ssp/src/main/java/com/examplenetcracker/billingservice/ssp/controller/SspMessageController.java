package com.examplenetcracker.billingservice.ssp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class SspMessageController {
    @GetMapping
    public String list() {
        return  "Привет мир, я SSP!";
    }
}
