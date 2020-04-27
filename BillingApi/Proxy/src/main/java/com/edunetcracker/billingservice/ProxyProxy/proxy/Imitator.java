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

import java.util.List;
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

        final ThreadLocalRandom currentRandom = ThreadLocalRandom.current();
        int maxToCreate = currentRandom.nextInt(0, 3);
        int prefix = currentRandom.nextInt(1000, 8999);

        for (int i = 0; i < maxToCreate; i++) {
            final int accUid = (prefix + i);
            final String phoneNum = "8801555" + accUid;
            final String login = "user" + accUid + "@mail.ru";
            if (!checks.isAccountExistsByPhone(phoneNum) && !checks.isAccountExists(login)) {
                final List<Tariff> tariffs = tariffController.getAllTariff().getBody();
                if(tariffs!=null && !tariffs.isEmpty()){
                    final Tariff currentTariff = tariffs.get(currentRandom.nextInt(0, tariffs.size() + 1));
                    if(currentTariff!=null){
                        final CollectedTariff collectedTariff = tariffController.getCollectedTariffByName(currentTariff.getName()).getBody();


                        if(collectedTariff!=null){

                            final TariffCall tariffCall = collectedTariff.getTariffCall();
                            final TariffSms tariffSms = collectedTariff.getTariffSms();
                            final TariffInternet tariffInternet = collectedTariff.getTariffInternet();

                            Account account = new Account();

                            account.setLogin(login);
                            account.setPassword("123456");
                            account.setName("user" + accUid);
                            account.setBalance(0L);

                            account.setTelephone(phoneNum);
                            account.setRang("USER");

                            account.setTariff(currentTariff.getName());

                            // взять тариф и присвоить его
                            Call call = new Call();
                            call.setLogin(account.getLogin());
                            call.setCall_cost(tariffCall.getCall_cost());
                            call.setCall_balance(currentRandom.nextLong(0, 100));
                            call.setDefault_call_cost(tariffCall.getDefault_call_cost());

                            Internet internet = new Internet();
                            internet.setLogin(account.getLogin());
                            internet.setInternet_cost(tariffInternet.getInternet_cost());
                            internet.setInternet_balance(currentRandom.nextLong(0, 50));
                            internet.setDefault_internet_cost(tariffInternet.getDefault_internet_cost());

                            Sms sms = new Sms();
                            sms.setLogin(account.getLogin());
                            sms.setSms_cost(tariffSms.getSms_cost());
                            sms.setSms_balance(currentRandom.nextLong(0, 50000));
                            sms.setDefault_sms_cost(tariffSms.getDefault_sms_cost());

                            rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                            rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
                            rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
                            rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);

                            LOG.info("Generated account - {} [sms:{}, calls:{}, internet:{}]", account, sms, call, internet);
                        }

                    }

                }


            }

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
