package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Checks {

    @Autowired
    Helpers helpers;

    /*************checks**************/
    // true - exist, false - not exist, null - error
    // check login in billing
    /*
    public Boolean isAccountExists(Account account){
        try {
            String url = getUrlBilling() + "/isAccountExists";
            Boolean isExist = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(account, new HttpHeaders()), Boolean.class).getBody(); //GET не работает
            if (isExist != null) {
                return  isExist;
            }
            else{
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
    public Boolean isAccountExists(String accountLogin) {
        try {
            String url = helpers.getUrlProxy() + "/getAccount/?login=" + accountLogin;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();
            if (account == null) {
                return true;
            }
            else{
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isAddAmountFeasible(String accountLogin, Long amount) {
        try {

            String url = helpers.getUrlProxy() + "/getBalance/?login=" + accountLogin;
            ResponseEntity<Long> responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);

            if(responseBalance.getBody()+amount >= 0L) {
                return true;
            }

            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean canAccountCall(Long accountBalance, Long costCall) {


        if(accountBalance - costCall >= 0L){
            return true;
        }
        return false;
    }


}
