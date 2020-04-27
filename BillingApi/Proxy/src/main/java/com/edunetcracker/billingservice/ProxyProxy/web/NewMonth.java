package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;

@RestController
public class NewMonth {

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

    Logger LOG = LoggerFactory.getLogger(NewMonth.class);

    @GetMapping("newMonth")
    @Scheduled(cron = "* */10 * * * *")
    public boolean newMonth(){
        LOG.info("newMonth started {}", Date.from(Instant.now()));

        List<Account> accounts = accountController.getAllAccount().getBody();   // получим имена тарифов
        List<CollectedTariff> collectedTariffs = tariffController.getAllCollectedTariff().getBody();    // получим деф значения

        for (Account account : accounts) {
            String tariffName = account.getTariff();
            for (CollectedTariff collectedTariff : collectedTariffs) {
                if(tariffName.equals(collectedTariff.getName())){

                    TariffCall tariffCall = collectedTariff.getTariffCall();
                    TariffInternet tariffInternet = collectedTariff.getTariffInternet();
                    TariffSms tariffSms = collectedTariff.getTariffSms();

                    Call call = new Call();
                    Internet internet = new Internet();
                    Sms sms = new Sms();

                    call.setLogin(account.getLogin());
                    call.setCall_balance(tariffCall.getCall_balance());
                    call.setCall_cost(tariffCall.getCall_cost());
                    call.setDefault_call_cost(tariffCall.getDefault_call_cost());

                    internet.setLogin(account.getLogin());
                    internet.setInternet_balance(tariffInternet.getInternet_balance());
                    internet.setInternet_cost(tariffInternet.getInternet_cost());
                    internet.setDefault_internet_cost(tariffInternet.getDefault_internet_cost());

                    sms.setLogin(account.getLogin());
                    sms.setSms_balance(tariffSms.getSms_balance());
                    sms.setSms_cost(tariffSms.getSms_cost());
                    sms.setDefault_sms_cost(tariffSms.getDefault_sms_cost());

                    try {
                        callController.updateCall(call);
                        internetController.updateInternet(internet);
                        smsController.updateSms(sms);
                        LOG.info("Updated account - {} [sms:{}, calls:{}, internet:{}]", account, sms, call, internet);
                        break;
                    }catch (Exception e){
                        LOG.info("Failed to update account - {} [sms:{}, calls:{}, internet:{}]", account, sms, call, internet);
                        break;
                    }
                }
            }
        }

        LOG.info("newMonth finished");
        return true;
    }

}
