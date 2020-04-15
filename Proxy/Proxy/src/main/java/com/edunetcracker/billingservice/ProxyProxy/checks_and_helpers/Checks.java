package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class Checks {

    @Autowired
    Helpers helpers;

    List<String> ranges = Arrays.asList("user","administrator");

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

    public Boolean isTariffExists(String tariffName) {
        try {
            String url = helpers.getUrlBilling() + "/getTariffByName/?name=" + tariffName;
            Tariff tariff  = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Tariff.class).getBody();
            if (tariff == null)
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isAddAmountFeasible(String accountLogin, Long amount) {
        try {

            String url = helpers.getUrlBilling() + "/getBalanceByLogin/?login=" + accountLogin;
            ResponseEntity<Long> responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);

            if (responseBalance.getBody() + amount >= 0L) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAvailableInRanges(String mainRang){
        for(int a = 0; a< ranges.size(); a++){
            if(mainRang.equals(ranges.get(a)))
                return true;
        }
        return false;
    }

}
