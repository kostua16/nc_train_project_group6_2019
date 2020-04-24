package com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public Account getAccount(String accountLogin){
        String url = getUrlBilling() + "/getAccountByLogin/?login=" + accountLogin;
        Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();
        return account;
    }
}
