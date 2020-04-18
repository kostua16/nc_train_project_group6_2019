package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.CollectedTariff;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    BalanceController balanceController;

    @Autowired
    CallController callController;

    @Autowired
    InternetController internetController;

    @Autowired
    SmsController smsController;

    @Autowired
    TariffController tariffController;

    @Autowired
    Checks checks;
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
        Account account = accountController.getAccount(login).getBody();
        Long internet = internetController.getInternetByLogin(login).getBody().getInternet_balance();
        Long minutes = callController.getCallByLogin(login).getBody().getCall_balance();
        Long sms = smsController.getSmsByLogin(login).getBody().getSms_balance();
        if(account != null && internet != null && minutes != null && sms != null) {
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("name", account.getName());
            returnMap.put("balance", account.getBalance().toString());
            returnMap.put("internet", internet.toString());
            returnMap.put("minutes", minutes.toString());
            returnMap.put("sms", sms.toString());
            //
            returnMap.put("telephone", account.getTelephone());
            //
            return returnMap;
        }
        return null;
    }
    //  http://localhost:8102/topup/?login=tester@mail.ru&amount=999
    @GetMapping("topup")
    public Boolean topup (@RequestParam("login") String login,
                          @RequestParam("amount") Long amount ) {
        Account account = accountController.getAccount(login).getBody();
        if (amount > 0L && balanceController.addToBalance(login, amount).getBody())
            return true;
        return false;
    }
    //  http://localhost:8102/showtariff/?login=tester@mail.ru
    @GetMapping("showtariff")
    public Map<String, Object> showtariff(@RequestParam("login") String login){
        String userTariff = accountController.getAccount(login).getBody().getTariff();
        List<CollectedTariff> tariffs = tariffController.getAllCollectedTariff().getBody();
        if (userTariff != null && tariffs != null) {
            Map<String, Map<String, String>> returnT = new HashMap<>();
            Map<String, String> tariff;
            /*Map<String, String> uTariff = new HashMap<>();
            uTariff.put("tariff", userTariff);
            returnT.put("user", uTariff);*/
            for (int a = 0; a< tariffs.size(); a++){
                tariff = new HashMap<>();
                tariff.put("call", tariffs.get(a).getTariffCall().getCall_balance().toString());
                tariff.put("internet", tariffs.get(a).getTariffInternet().getInternet_balance().toString());
                tariff.put("sms", tariffs.get(a).getTariffSms().getSms_balance().toString());
                returnT.put(tariffs.get(a).getName(), tariff);
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("user", userTariff);
            returnMap.put("tariffs", returnT);
            return returnMap;
        }
        return null;
    }
    //  http://localhost:8102/choicetariff/?login=tester@mail.ru&tariff=FOR_SMALL
    @GetMapping("choicetariff")
    public Boolean choicetariff(@RequestParam("login") String login,
                                @RequestParam("tariff") String tariff){
        Account account = accountController.getAccount(login).getBody();
        if(account != null) {
            account.setTariff(tariff);
            accountController.updateAccount(account);
            return true;
        }
        return false;
    }
    @GetMapping("getTelephone")
    public String getTelephone(@RequestParam("login") String login){
        Account account = accountController.getAccount(login).getBody();
        if(account != null) {
            return account.getTariff();
        }
        return null;
    }

}
