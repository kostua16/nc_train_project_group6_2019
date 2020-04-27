package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.proxy.CallController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class Checks {

    @Autowired
    Helpers helpers;

    List<String> ranges = Arrays.asList("user","administrator");

    Logger LOG = LoggerFactory.getLogger(Checks.class);

    /*************checks**************/
    public Boolean isAccountExists(String accountLogin) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + accountLogin;
            final ResponseEntity<Account> result = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            if(HttpStatus.NOT_FOUND.equals(result.getStatusCode())){
                return false;
            } else {
                Account account = result.getBody();
                LOG.info("isAccountExists" + (account!=null ? account.getBalance() : null));
                return account != null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isAccountExistsByPhone(String phoneNum) {
        try {
            String url = helpers.getUrlBilling() + "/getAccountByTelephone/?telephone=" + phoneNum;
            final ResponseEntity<Account> result = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            if(HttpStatus.NOT_FOUND.equals(result.getStatusCode())){
                return false;
            } else {
                Account account = result.getBody();
                LOG.info("isAccountExistsByPhone" + (account!=null ? account.getBalance() : null));
                return account != null;
            }
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
