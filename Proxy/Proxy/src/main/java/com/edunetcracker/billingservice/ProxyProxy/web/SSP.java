package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.proxy.TariffController;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSP {
    //TODO  operation with accounts
    //TODO  operation with tariffs

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @Autowired
    TariffController tariffController;

    //  http://localhost:8102/start
    @GetMapping("start")
    public Boolean start() throws JsonProcessingException {
        if(!checks.isAccountExists("tester@mail.ru") &&
            !checks.isTariffExists("DEFAULT")) {
            Account account = new Account();
            account.setLogin("tester@mail.ru");
            account.setPassword("123");
            account.setName("Piter");
            account.setBalance(0L);
            account.setTariff("DEFAULT");
            account.setRang("base_user");
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            Tariff tariff = new Tariff();
            tariff.setName("DEFAULT");
            rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
            /****/
            TariffCall tariffCall = new TariffCall();
            tariffCall.setName("DEFAULT");
            tariffCall.setCall_cost(2.2F);
            tariffCall.setCall_balance(1222333L);
            tariffCall.setDefault_call_cost(3.5F);
            rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
            TariffInternet tariffInternet = new TariffInternet();
            tariffInternet.setName("DEFAULT");
            tariffInternet.setInternet_cost(2.2F);
            tariffInternet.setInternet_balance(12223333L);
            tariffInternet.setDefault_internet_cost(3.5F);
            rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            TariffSms tariffSms = new TariffSms();
            tariffSms.setName("DEFAULT");
            tariffSms.setSms_cost(2.2F);
            tariffSms.setSms_balance(100L);
            tariffSms.setDefault_sms_cost(3.5F);
            rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
            /****/
            Call call = new Call();
            call.setLogin("tester@mail.ru");
            call.setCall_cost(2.2F);
            call.setCall_balance(1222333L);
            call.setDefault_call_cost(3.5F);
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            Internet internet = new Internet();
            internet.setLogin("tester@mail.ru");
            internet.setInternet_cost(2.2F);
            internet.setInternet_balance(12223333L);
            internet.setDefault_internet_cost(3.5F);
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            Sms sms = new Sms();
            sms.setLogin("tester@mail.ru");
            sms.setSms_cost(2.2F);
            sms.setSms_balance(100L);
            sms.setDefault_sms_cost(3.5F);
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
            return true;
        }
        return false;
    }
    //  http://localhost:8102/createA/?login=hhh@mail.ru&password=333&name=Tom&tariff=DEFAULT
    @GetMapping("createA")
    public Boolean createA(@RequestParam("login") String login,
                           @RequestParam("password") String password,
                           @RequestParam("name") String name,
                           @RequestParam("tariff") String tariff) throws JsonProcessingException {
        // если нет аккаунта, но есть тариф
        System.out.println("createA");
        if(!checks.isAccountExists(login) && checks.isTariffExists(tariff)) {
            System.out.println("createA !");
            Account account = new Account();
            account.setLogin(login);
            account.setPassword(password);
            account.setName(name);
            account.setBalance(0L);
            account.setTariff(tariff);
            account.setRang("base_user");
            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            CollectedTariff collectedTariff = tariffController.getCollectedTariffByName(tariff).getBody();
            /****/
            Call call = new Call();
            call.setLogin(login);
            call.setCall_cost(collectedTariff.getTariffCall().getCall_cost());
            call.setCall_balance(collectedTariff.getTariffCall().getCall_balance());
            call.setDefault_call_cost(collectedTariff.getTariffCall().getDefault_call_cost());
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            Internet internet = new Internet();
            internet.setLogin(login);
            internet.setInternet_cost(collectedTariff.getTariffInternet().getInternet_cost());
            internet.setInternet_balance(collectedTariff.getTariffInternet().getInternet_balance());
            internet.setDefault_internet_cost(collectedTariff.getTariffInternet().getDefault_internet_cost());
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            Sms sms = new Sms();
            sms.setLogin(login);
            sms.setSms_cost(collectedTariff.getTariffSms().getSms_cost());
            sms.setSms_balance(collectedTariff.getTariffSms().getSms_balance());
            sms.setDefault_sms_cost(collectedTariff.getTariffSms().getDefault_sms_cost());
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
            return true;
        }
        return false;
    }

    //  http://localhost:8102/createT/?tariff=FOR_SMALL
    @GetMapping("createT")
    public Boolean createT(@RequestParam("tariff") String tariffname) throws JsonProcessingException {
        if(!checks.isTariffExists(tariffname)){
            Tariff tariff = new Tariff();
            tariff.setName(tariffname);
            rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
            /****/
            TariffCall tariffCall = new TariffCall();
            tariffCall.setName(tariffname);
            tariffCall.setCall_cost(2.2F);
            tariffCall.setCall_balance(1222333L);
            tariffCall.setDefault_call_cost(3.5F);
            rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
            TariffInternet tariffInternet = new TariffInternet();
            tariffInternet.setName(tariffname);
            tariffInternet.setInternet_cost(2.2F);
            tariffInternet.setInternet_balance(12223333L);
            tariffInternet.setDefault_internet_cost(3.5F);
            rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
            TariffSms tariffSms = new TariffSms();
            tariffSms.setName(tariffname);
            tariffSms.setSms_cost(2.2F);
            tariffSms.setSms_balance(100L);
            tariffSms.setDefault_sms_cost(3.5F);
            rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
            return true;
        }
        return false;
    }
}
