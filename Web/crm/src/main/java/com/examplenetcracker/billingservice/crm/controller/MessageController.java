package com.examplenetcracker.billingservice.crm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    @GetMapping
    public String list() {
        return  "Привет мир, я CRM!";
    }
}
