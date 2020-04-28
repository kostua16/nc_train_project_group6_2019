package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImitatorService {

    @Autowired
    TariffController tariffController;

    @Autowired
    AccountController accountController;



    Logger LOG = LoggerFactory.getLogger(ImitatorService.class);

    private boolean started = false;

    private boolean systemInit = false;

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;
    }

    public void manuallyGenerateUsers() throws JsonProcessingException {
        final ThreadLocalRandom currentRandom = ThreadLocalRandom.current();
        int maxToCreate = currentRandom.nextInt(0, 3);
        int prefix = currentRandom.nextInt(1000, 8999);

        for (int i = 0; i < maxToCreate; i++) {
            final int accUid = (prefix + i);
            final String phoneNum = "8801555" + accUid;
            final String login = "user" + accUid + "@mail.ru";
            if (!accountController.isAccountExistsByPhone(phoneNum) && !accountController.isAccountExists(login)) {
                final List<Tariff> tariffs = tariffController.getAllBaseTariffs();
                if (tariffs != null && !tariffs.isEmpty()) {
                    final Tariff currentTariff = tariffs.get(currentRandom.nextInt(0, tariffs.size() + 1));
                    if (currentTariff != null) {
                        final CollectedTariff collectedTariff = tariffController.getTariff(currentTariff.getName());


                        if (collectedTariff != null) {

                            Account account = new Account();

                            account.setLogin(login);
                            account.setPassword("123456");
                            account.setName("user" + accUid);
                            account.setBalance(currentRandom.nextLong(0, 100));

                            account.setTelephone(phoneNum);
                            account.setRang("USER");

                            account.setTariff(currentTariff.getName());

                            accountController.createAccount(account);

                            LOG.info("Generated account - {}", account);
                        }

                    }
                }
            }
        }
    }

    public void manuallyMadeCalls() {
        int maxToRun = ThreadLocalRandom.current().nextInt(0, 15);
        int maxToIterate = ThreadLocalRandom.current().nextInt(0, 25);
        for (int i = 0; i < maxToRun; i++) {
            int prefix = ThreadLocalRandom.current().nextInt(1000, 8999);
            for (int j = 0; j < maxToIterate; j++) {
                int accUid = (prefix + j);
                String phoneNum = "8801555" + accUid;
                final Account accountFrom = accountController.getAccountByTelephone(phoneNum);
                if (accountFrom != null) {
                    switch (ThreadLocalRandom.current().nextInt(1, 4)) {
                        case 1:
                            accountController.chargeCall(accountFrom, ThreadLocalRandom.current().nextInt(1, 3));
                            break;
                        case 2:
                            accountController.chargeSms(accountFrom, ThreadLocalRandom.current().nextLong(1, 3));
                            break;
                        case 3:
                            accountController.chargeInternet(accountFrom, ThreadLocalRandom.current().nextLong(100, 30000));
                            break;
                    }
                }
            }
        }
    }

    public Boolean initBaseUsers() {

        try {
            if (!tariffController.isTariffExists("DEFAULT")) {
                tariffController.createTariff(
                        new CollectedTariff("DEFAULT",
                                new TariffCall("DEFAULT", 0F, 1800L, 0.0834F),
                                new TariffInternet("DEFAULT", 0F, 1000000L, 0.001F),
                                new TariffSms("DEFAULT", 0F, 30L, 2F)
                        ));
            }
            if (!tariffController.isTariffExists("ADMINISTRATOR")) {
                tariffController.createTariff(
                        new CollectedTariff("ADMINISTRATOR",
                                new TariffCall("ADMINISTRATOR", 0F, 0L, 0F),
                                new TariffInternet("ADMINISTRATOR", 0F, 0L, 0F),
                                new TariffSms("ADMINISTRATOR", 0F, 0L, 0F)
                        ));
            }

            if (!accountController.isAccountExists("admin@mail.ru")) {
                accountController.createAccount(
                        new Account(
                                "admin@mail.ru", "123456", "admin",
                                0L, "ADMINISTRATOR", "88005553535", "ADMINISTRATOR"
                        )
                );
            }

            if (!accountController.isAccountExists("user@mail.ru")) {
                accountController.createAccount(
                        new Account(
                                "user@mail.ru", "123456", "user",
                                0L, "DEFAULT", "88005553536", "USER"
                        )
                );
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    public void initSystem() {
        if (!systemInit) {
            systemInit = initBaseUsers();
        }

    }

    @Scheduled(cron = "* */1 * * * *")
    public void generateUsers() throws JsonProcessingException {
        if (started) {
            manuallyGenerateUsers();
        }
    }


    @Scheduled(cron = "* */1 * * * *")
    public void madeCalls() {
        if (started) {
            manuallyMadeCalls();
        }
    }
}
