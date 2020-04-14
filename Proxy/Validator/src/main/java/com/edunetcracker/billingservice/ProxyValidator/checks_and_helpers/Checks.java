package com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Checks {

    @Autowired
    Helpers helpers;

    @Autowired
    SessionService sessionService;

    /*************checks**************/
    public Boolean isAccountExists(String accountLogin) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + accountLogin;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();
            System.out.println("isAccountExists" + account.getBalance());
            if (account == null)
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkByToken(String token) {
        if (sessionService.inSession(token))
            return true;
        return false;
    }

    public String getLoginByToken(String token) {
        if (checkByToken(token)) {
            String login = sessionService.getLogin(token).getLogin();
            if (isAccountExists(login)) {
                return login;
            }
        }
        return null;
    }

}
