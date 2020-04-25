package com.edunetcracker.billingservice.BillingDB;

import org.springframework.web.bind.annotation.*;


@RestController
public class TestController {
        @GetMapping("test")
        public String t() {
            return "Good";
        }
        @GetMapping("")
        public String u() {
        return "Hello Billing";
    }
}
