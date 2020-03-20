package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Helpers {


    @Value("${app.billing.host}")
    private String hostBilling = "localhost";

    @Value("${app.billing.port}")
    private String portBilling = "8080";

    @Value("${server.address}")
    private String hostProxy = "localhost";

    @Value("${server.port}")
    private String portProxy = "8102";


    /*************helpers**************/
    public String getUrlBilling (){
        return "http://"+ hostBilling + ":" + portBilling;
    }

    public String getUrlProxy (){
        return "http://"+ hostProxy + ":" + portProxy;
    }


}
