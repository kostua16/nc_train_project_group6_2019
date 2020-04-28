package com.edunetcracker.billingservice.ProxyProxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;
    @Autowired
    private RabbitMQSender rabbitMQSender;

        @GetMapping("test")
        public String t() {
            return "Good";
        }
        @GetMapping("")
        public String u() {
        return "Hello Proxy";
    }
        @GetMapping("start")
        public Boolean start() throws JsonProcessingException {
            if (!checks.isTariffExists("DEFAULT")) {
                Tariff tariff = new Tariff();
                tariff.setName("DEFAULT");
                rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
                TariffCall tariffCall = new TariffCall();
                tariffCall.setName("DEFAULT");
                tariffCall.setCall_cost(0F);
                tariffCall.setCall_balance(1800L);
                tariffCall.setDefault_call_cost(0.0834F);
                rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
                TariffInternet tariffInternet = new TariffInternet();
                tariffInternet.setName("DEFAULT");
                tariffInternet.setInternet_cost(0F);
                tariffInternet.setInternet_balance(1000000L);
                tariffInternet.setDefault_internet_cost(0.001F);
                rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
                TariffSms tariffSms = new TariffSms();
                tariffSms.setName("DEFAULT");
                tariffSms.setSms_cost(0F);
                tariffSms.setSms_balance(30L);
                tariffSms.setDefault_sms_cost(2F);
                rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
            }
            if (!checks.isTariffExists("ADMINISTRATOR")) {
                Tariff tariff = new Tariff();
                tariff.setName("ADMINISTRATOR");
                rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
                TariffCall tariffCall = new TariffCall();
                tariffCall.setName("ADMINISTRATOR");
                tariffCall.setCall_cost(0F);
                tariffCall.setCall_balance(0L);
                tariffCall.setDefault_call_cost(0F);
                rabbitMQSender.send(tariffCall, RabbitMQMessageType.CREATE_TARIFF_CALL);
                TariffInternet tariffInternet = new TariffInternet();
                tariffInternet.setName("ADMINISTRATOR");
                tariffInternet.setInternet_cost(0F);
                tariffInternet.setInternet_balance(0L);
                tariffInternet.setDefault_internet_cost(0F);
                rabbitMQSender.send(tariffInternet, RabbitMQMessageType.CREATE_TARIFF_INTERNET);
                TariffSms tariffSms = new TariffSms();
                tariffSms.setName("ADMINISTRATOR");
                tariffSms.setSms_cost(0F);
                tariffSms.setSms_balance(0L);
                tariffSms.setDefault_sms_cost(0F);
                rabbitMQSender.send(tariffSms, RabbitMQMessageType.CREATE_TARIFF_SMS);
            }
            if (!checks.isAccountExists("admin@mail.ru")) {
                Account account = new Account();
                account.setLogin("admin@mail.ru");
                account.setPassword("123456");
                account.setName("admin");
                account.setBalance(0L);
                account.setTariff("ADMINISTRATOR");
                account.setTelephone("88005553535");
                account.setRang("ADMINISTRATOR");
                rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);

                Call call = new Call();
                call.setLogin("admin@mail.ru");
                call.setCall_cost(0F);
                call.setCall_balance(0L);
                call.setDefault_call_cost(0F);
                rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
                Internet internet = new Internet();
                internet.setLogin("admin@mail.ru");
                internet.setInternet_cost(0F);
                internet.setInternet_balance(0L);
                internet.setDefault_internet_cost(0F);
                rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
                Sms sms = new Sms();
                sms.setLogin("admin@mail.ru");
                sms.setSms_cost(0F);
                sms.setSms_balance(0L);
                sms.setDefault_sms_cost(0F);
                rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
            }
            if (!checks.isAccountExists("user@mail.ru")){
                Account account = new Account();
                account.setLogin("user@mail.ru");
                account.setPassword("123456");
                account.setName("user");
                account.setBalance(0L);
                account.setTariff("DEFAULT");
                account.setTelephone("88005553536");
                account.setRang("USER");
                rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                // взять тариф и присвоить его
                Call call = new Call();
                call.setLogin("user@mail.ru");
                call.setCall_cost(0F);
                call.setCall_balance(1800L);
                call.setDefault_call_cost(0.0834F);
                rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
                Internet internet = new Internet();
                internet.setLogin("user@mail.ru");
                internet.setInternet_cost(0F);
                internet.setInternet_balance(1000000L);
                internet.setDefault_internet_cost(0.001F);
                rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
                Sms sms = new Sms();
                sms.setLogin("user@mail.ru");
                sms.setSms_cost(0F);
                sms.setSms_balance(30L);
                sms.setDefault_sms_cost(2F);
                rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);

            }
            return true;
        }
}
