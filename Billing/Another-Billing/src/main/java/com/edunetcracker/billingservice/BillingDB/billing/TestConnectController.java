package com.edunetcracker.billingservice.BillingDB.billing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConnectController {

    @GetMapping("isConnect")
    public ResponseEntity<Boolean> isConnect() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
