package com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Helpers {


    @Value("${app.billing.host}")
    private String hostBilling = "localhost";

    @Value("${app.billing.port}")
    private String portBilling = "8202";

    @Value("${app.proxy_proxy.host}")
    private String hostProxy = "localhost";

    @Value("${app.proxy_proxy.port}")
    private String portProxy = "8102";


    /*************helpers**************/
    public String getUrlBilling() {
        return "http://" + hostBilling + ":" + portBilling;
    }

    public String getUrlProxy() {
        return "http://" + hostProxy + ":" + portProxy;
    }


}
