package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.CollectedTariff;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SSP {
    //TODO  get balance and services status
    //TODO  choice a tariff
    //TODO  depositing to balance

    @Autowired
    AccountController accountController;
    @Autowired
    TariffController tariffController;


    Logger LOG = LoggerFactory.getLogger(SSP.class);
    /***************************************************/
    /**
     * AccountName
     * Balance
     * Remains:
     *          Internet
     *          Minutes
     *          SMS
     */
    //  http://localhost:8102/home/?login=tester@mail.ru
    @GetMapping("home")
    public Map<String, String> home(@RequestParam("login") String login){
        Map<String, String> returnMap = new HashMap<>();
        Account account = accountController.getAccount(login);
        if(account!=null){
            Long internet = accountController.getInternetBalance(login).getInternet_balance();
            Long minutes = accountController.getCallBalance(login).getCall_balance();
            Long sms = accountController.getSmsBalance(login).getSms_balance();
            if(internet != null && minutes != null && sms != null) {
                returnMap.put("name", account.getName());
                returnMap.put("balance", account.getBalance().toString());
                returnMap.put("internet", internet.toString());
                returnMap.put("minutes", minutes.toString());
                returnMap.put("sms", sms.toString());
                returnMap.put("telephone", account.getTelephone());
            }
        }

        return returnMap;
    }
    //  http://localhost:8102/topup/?login=tester@mail.ru&amount=999
    @GetMapping("topup")
    public Boolean topup (@RequestParam("login") String login,
                          @RequestParam("amount") Long amount ) {
        return accountController.addBalance(login, amount);
    }
    //  http://localhost:8102/showtariff/?login=tester@mail.ru
    @GetMapping("showtariff")
    public Map<String, Object> showtariff(@RequestParam("login") String login){
        Map<String, Object> returnMap = new HashMap<>();
        final Account account = accountController.getAccount(login);
        if(account!=null){
            returnMap.put("user", account.getTariff());
            returnMap.put("tariffs", tariffController.getAllCollectedTariffAsMapList());
        }
        return returnMap;
    }
    //  http://localhost:8102/choicetariff/?login=tester@mail.ru&tariff=FOR_SMALL
    @GetMapping("choicetariff")
    public Boolean choicetariff(@RequestParam("login") String login,
                                @RequestParam("tariff") String tariff){
        Account account = accountController.getAccount(login);
        if(account != null) {
            account.setTariff(tariff);
            accountController.updateAccount(account);
            return true;
        }
        return false;
    }
    @GetMapping("getTelephone")
    public String getTelephone(@RequestParam("login") String login){
        Account account = accountController.getAccount(login);
        if(account != null) {
            return account.getTelephone();
        }
        return null;
    }

}
