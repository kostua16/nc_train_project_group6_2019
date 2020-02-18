package com.edunetcracker.billingservice.BillingDB.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO

@RestController
@RequestMapping("/api/billing-db")
@Validated
public class BillingDBController {

    @Autowired
    private IAccountRepository repository=null;

    Logger logger = LoggerFactory.getLogger(BillingDBController.class);



}
