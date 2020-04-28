package com.edunetcracker.billingservice.ProxyProxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
        @GetMapping("test")
        public String t() {
            return "Good";
        }
        @GetMapping("")
        public String u() {
        return "Hello Proxy";
    }
}
