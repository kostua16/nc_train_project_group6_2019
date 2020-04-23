package com.edunetcracker.billingservice.BillingDB;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
        @GetMapping("test")
        public String t() {
            return "Good";
        }
        @GetMapping("")
        public String u() {
        return "Hello";
    }


}
