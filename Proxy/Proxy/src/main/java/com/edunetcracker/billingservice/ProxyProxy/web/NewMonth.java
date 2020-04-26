package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("newMonth")
    public boolean newMonth(){
        Call call = new Call();
        Internet internet = new Internet();
        Sms sms = new Sms();
        List<Account> accounts = accountController.getAllAccount().getBody();   // получим имена тарифов
        List<CollectedTariff> collectedTariffs = tariffController.getAllCollectedTariff().getBody();    // получим деф значения

        for (int a = 0; a< accounts.size(); a++){
            String tariffName = accounts.get(a).getTariff();
            for (int m = 0; m< collectedTariffs.size(); m++){
                if(tariffName.equals(collectedTariffs.get(m).getName())){

                    TariffCall tariffCall = collectedTariffs.get(m).getTariffCall();
                    TariffInternet tariffInternet = collectedTariffs.get(m).getTariffInternet();
                    TariffSms tariffSms = collectedTariffs.get(m).getTariffSms();

                    call.setLogin(accounts.get(a).getLogin());
                    call.setCall_balance(tariffCall.getCall_balance());
                    call.setCall_cost(tariffCall.getCall_cost());
                    call.setDefault_call_cost(tariffCall.getDefault_call_cost());

                    internet.setLogin(accounts.get(a).getLogin());
                    internet.setInternet_balance(tariffInternet.getInternet_balance());
                    internet.setInternet_cost(tariffInternet.getInternet_cost());
                    internet.setDefault_internet_cost(tariffInternet.getDefault_internet_cost());

                    sms.setLogin(accounts.get(a).getLogin());
                    sms.setSms_balance(tariffSms.getSms_balance());
                    sms.setSms_cost(tariffSms.getSms_cost());
                    sms.setDefault_sms_cost(tariffSms.getDefault_sms_cost());

                    try {
                        callController.updateCall(call);
                        internetController.updateInternet(internet);
                        smsController.updateSms(sms);
                        break;
                    }catch (Exception e){
                        System.out.println("Failed to update account");
                        break;
                    }
                }
            }

        }


        return true;
    }

}
