package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.proxy.BillingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewMonth {


    @Autowired
    BillingController billingController;

    Logger LOG = LoggerFactory.getLogger(NewMonth.class);

    @GetMapping("newMonth")
    public boolean newMonth() {
        return billingController.newMonth();
    }

}
