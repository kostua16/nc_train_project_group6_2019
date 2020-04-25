package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//АТС
@RestController
public class TelephoneExchange {
    //TODO //addBalance
    //TODO //CALL
    //TODO //INTERNET
    //TODO //SMS
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

    Logger LOG = LoggerFactory.getLogger(TelephoneExchange.class);

    /*************************************************************************/
    //  http://localhost:8102/addBalance/?telephoneFrom=897654321&amount=1
    @GetMapping("addBalance")
    public Boolean topup (@RequestParam("telephoneFrom") String telephoneFrom,
                          @RequestParam("amount") Long amount ) {
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom).getBody();
        if (amount > 0L && balanceController.addToBalance(accountFrom.getLogin(), amount).getBody())
            return true;
        return false;
    }
    //  http://localhost:8102/callFromTo/?telephoneFrom=897654321&minutes=1&telephoneTo=999
    @GetMapping("callFromTo")
    public Boolean choicetariff(@RequestParam("telephoneFrom") String telephoneFrom,
                                @RequestParam("minutes") Long minutes,
                                @RequestParam("telephoneTo") String telephoneTo){
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom).getBody();
        //return callController.callToMinutes(accountFrom.getLogin(), Long.parseLong(minutes));
        return callController.callToMinutes(accountFrom.getLogin(), minutes);
    }
    //  http://localhost:8102/useInternet/?telephoneFrom=897654321&kilobytes=4
    @GetMapping("useInternet")
    public Boolean useInternet(@RequestParam("telephoneFrom") String telephoneFrom,
                               @RequestParam("kilobytes") Long kilobytes){
        LOG.info("i");
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom).getBody();
        LOG.info("ii");
        return internetController.useInternetKilobytes(accountFrom.getLogin(), kilobytes);
    }
    //  http://localhost:8102/smsFromTo/?telephoneFrom=897654321&sms=4&telephoneTo=999
    @GetMapping("smsFromTo")
    public Boolean smsFromTo(@RequestParam("telephoneFrom") String telephoneFrom,
                             @RequestParam("sms") Long sms,
                             @RequestParam("telephoneTo") String telephoneTo){
        Account accountFrom = accountController.getAccountByTelephone(telephoneFrom).getBody();
        return smsController.requestSms(accountFrom.getLogin(),sms);
    }
}
