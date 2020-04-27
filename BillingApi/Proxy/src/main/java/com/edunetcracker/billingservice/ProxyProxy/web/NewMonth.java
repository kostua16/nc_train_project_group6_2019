package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

@RestController
public class NewMonth {


    @Autowired
    BillingController billingController;

    Logger LOG = LoggerFactory.getLogger(NewMonth.class);

    @GetMapping("newMonth")
    public boolean newMonth(){
        return billingController.newMonth();
    }

}
