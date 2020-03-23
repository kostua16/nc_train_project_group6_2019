package com.edunetcracker.billingservice.ProxyValidator.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Helpers {


    @Value("${app.proxy.host}")
    private String hostProxy = "localhost";

    @Value("${app.proxy.port}")
    private String portProxy = "8102";

    @Value("${server.address}")
    private String hostValidator = "localhost";

    @Value("${server.port}")
    private String portValidator = "8101";


    /*************helpers**************/
    public String getUrlValidator() {
        return "http://" + hostValidator + ":" + portValidator;
    }

    public String getUrlProxy() {
        return "http://" + hostProxy + ":" + portProxy;
    }


}
