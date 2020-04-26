package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.web.CRM;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class Imitator {
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @Autowired
    TariffController tariffController;

    @Autowired
    AccountController accountController;

    @Autowired
    CallController callController;

    @Autowired
    InternetController internetController;

    @Autowired
    SmsController smsController;

    Logger LOG = LoggerFactory.getLogger(Imitator.class);


    @Scheduled(cron = "* */1 * * * *")
    public void generateUsers() throws JsonProcessingException {

        int maxToCreate = ThreadLocalRandom.current().nextInt(0, 10);
        int prefix = ThreadLocalRandom.current().nextInt(1000, 8999);

        for (int i = 0; i < maxToCreate; i++) {
            int accUid = (prefix + i);
            Account account = new Account();
            account.setLogin("user" + accUid + "@mail.ru");
            account.setPassword("123456");
            account.setName("user" + accUid);
            account.setBalance(0L);
            account.setTariff("DEFAULT");
            account.setTelephone("8801555" + accUid);
            account.setRang("USER");

            // взять тариф и присвоить его
            Call call = new Call();
            call.setLogin(account.getLogin());
            call.setCall_cost(0F);
            call.setCall_balance(1800L);
            call.setDefault_call_cost(0.0834F);

            Internet internet = new Internet();
            internet.setLogin(account.getLogin());
            internet.setInternet_cost(0F);
            internet.setInternet_balance(1000000L);
            internet.setDefault_internet_cost(0.001F);

            Sms sms = new Sms();
            sms.setLogin(account.getLogin());
            sms.setSms_cost(0F);
            sms.setSms_balance(30L);
            sms.setDefault_sms_cost(2F);

            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);

            LOG.info("Generated account - {} [sms:{}, calls:{}, internet:{}]", account, sms, call, internet);
        }
    }


    @Scheduled(cron = "* */1 * * * *")
    public void madeCalls() {

        int maxToRun = ThreadLocalRandom.current().nextInt(0, 15);
        for (int i = 0; i < maxToRun; i++) {
            int prefix = ThreadLocalRandom.current().nextInt(1000, 8999);
            for (int j = 0; j < 51; j++) {
                int accUid = (prefix + j);
                String phoneNum = "8801555" + accUid;
                final ResponseEntity<Account> accountByTelephone = accountController.getAccountByTelephone(phoneNum);
                if(HttpStatus.OK.equals(accountByTelephone.getStatusCode())){
                    Account accountFrom = accountByTelephone.getBody();
                    final String login = accountFrom.getLogin();
                    switch (ThreadLocalRandom.current().nextInt(1, 4)){
                        case 1: callController.callToMinutes(login, ThreadLocalRandom.current().nextInt(1, 3)); break;
                        case 2: smsController.requestSms(login, ThreadLocalRandom.current().nextLong(1, 3)); break;
                        case 3: internetController.useInternetKilobytes(login, ThreadLocalRandom.current().nextLong(100, 30000)); break;
                    }

                }

            }

        }



    }
}
