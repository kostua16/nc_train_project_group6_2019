package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Helpers {

    @Value("${app.billing.url}")
    private String billingUrl = "http://localhost:8202";

    /*************helpers**************/
    public String getUrlBilling() {
        return billingUrl;
    }


}
