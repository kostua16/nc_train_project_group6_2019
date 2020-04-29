package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.proxy.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//АТС
@RestController
public class TelephoneExchange {
    //TODO //addBalance
    //TODO //CALL
    //TODO //INTERNET
    //TODO //SMS
    @Autowired
    AccountController accountController;

    Logger LOG = LoggerFactory.getLogger(TelephoneExchange.class);

    //  http://localhost:8102/callFromTo/?telephoneFrom=897654321&minutes=1&telephoneTo=999
    @GetMapping("callFromTo")
    public Map<String, String> callFromTo(@RequestParam("telephoneFrom") String telephoneFrom,
                                          @RequestParam("minutes") Long minutes,
                                          @RequestParam("telephoneTo") String telephoneTo) {
        Map<String, String> result = new HashMap<>();
        result.put("stopped", "true");
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom);
        if (accountController.callCanBeDone(accountFrom, 1)) {
            int countDone = 0;
            boolean running = true;

            while (countDone <= minutes && running) {
                running = accountController.chargeCall(accountFrom, 60);
                countDone += 1;
            }
            result.put("count", String.valueOf(countDone));
            result.put("stopped", String.valueOf(!running));
            result.put("balance", String.valueOf(accountController.getAccountByTelephone(telephoneFrom).getBalance()));
            result.put("package", String.valueOf(accountController.getCallBalance(accountFrom.getLogin()).getCall_balance()));
        }
        LOG.info("callFromTo({}, {}, {}) result => {}", telephoneFrom, minutes, telephoneTo, result);
        return result;
    }

    //  http://localhost:8102/useInternet/?telephoneFrom=897654321&kilobytes=4
    @GetMapping("useInternet")
    public Map<String, String> useInternet(@RequestParam("telephoneFrom") String telephoneFrom,
                                           @RequestParam("kilobytes") Long kilobytes) {
        Map<String, String> result = new HashMap<>();
        result.put("stopped", "true");
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom);
        if (accountController.internetCanBeUsed(accountFrom, 1)) {
            int countDone = 0;
            boolean running = true;

            while (countDone <= kilobytes && running) {
                running = accountController.chargeInternet(accountFrom, 1024);
                countDone += 1024;
            }
            result.put("count", String.valueOf(countDone));
            result.put("stopped", String.valueOf(!running));
            result.put("balance", String.valueOf(accountController.getAccountByTelephone(telephoneFrom).getBalance()));
            result.put("package", String.valueOf(accountController.getInternetBalance(accountFrom.getLogin()).getInternet_balance()));
        }
        LOG.info("useInternet({}, {}) result => {}", telephoneFrom, kilobytes, result);
        return result;
    }

    //  http://localhost:8102/smsFromTo/?telephoneFrom=897654321&sms=4&telephoneTo=999
    @GetMapping("smsFromTo")
    public Map<String, String> smsFromTo(@RequestParam("telephoneFrom") String telephoneFrom,
                                         @RequestParam("sms") Long sms,
                                         @RequestParam("telephoneTo") String telephoneTo) {
        Map<String, String> result = new HashMap<>();
        result.put("stopped", "true");
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom);
        if (accountController.smsCanBeSend(accountFrom, 1)) {
            int countDone = 0;
            boolean running = true;

            while (countDone <= sms && running) {
                running = accountController.chargeSms(accountFrom, 1);
                countDone += 1;
            }
            result.put("count", String.valueOf(countDone));
            result.put("stopped", String.valueOf(!running));
            result.put("balance", String.valueOf(accountController.getAccountByTelephone(telephoneFrom).getBalance()));
            result.put("package", String.valueOf(accountController.getSmsBalance(accountFrom.getLogin()).getSms_balance()));
        }
        LOG.info("smsFromTo({}, {}, {}) result => {}", telephoneFrom, sms, telephoneTo, result);
        return result;
    }
}
