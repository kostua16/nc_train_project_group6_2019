package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/*@RestController*/
@Service
public class AccountController {

    @Autowired
    private RabbitMQSender rabbitMQSender;


    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    OperationsService operationsService;

    @Autowired
    TariffController tariffController;


    /////////////////////////////////////////////////////////////////////////////////////////////

    /*************checks**************/
    public Boolean isAccountExists(String accountLogin) {
        return operationsService.request("/getAccountByLogin/?login=" + accountLogin, HttpMethod.GET, Account.class) != null;
    }

    public Boolean isAccountExistsByPhone(String phoneNum) {
        return operationsService.request("/getAccountByTelephone/?telephone=" + phoneNum, HttpMethod.GET, Account.class) != null;
    }

    public Boolean createAccount(Account account) throws JsonProcessingException {
        if (account != null && !isAccountExists(account.getLogin()) && !isAccountExistsByPhone(account.getTelephone()) && tariffController.isTariffExists(account.getTariff())) {

            final CollectedTariff currentTarrif = tariffController.getTariff(account.getTariff());
            if (currentTarrif != null) {
                final TariffInternet tariffInternet = currentTarrif.getTariffInternet();
                final TariffSms tariffSms = currentTarrif.getTariffSms();
                final TariffCall tariffCall = currentTarrif.getTariffCall();
                // взять тариф и присвоить его
                Call call = new Call();
                call.setLogin(account.getLogin());
                call.setCall_cost(tariffCall.getCall_cost());
                call.setCall_balance(tariffCall.getCall_balance());
                call.setDefault_call_cost(tariffCall.getDefault_call_cost());

                Internet internet = new Internet();
                internet.setLogin(account.getLogin());
                internet.setInternet_cost(tariffInternet.getInternet_cost());
                internet.setInternet_balance(tariffInternet.getInternet_balance());
                internet.setDefault_internet_cost(tariffInternet.getDefault_internet_cost());

                Sms sms = new Sms();
                sms.setLogin(account.getLogin());
                sms.setSms_cost(tariffSms.getSms_cost());
                sms.setSms_balance(tariffSms.getSms_balance());
                sms.setDefault_sms_cost(tariffSms.getDefault_sms_cost());

                rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);
                rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
                rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);
                return true;
            }


        }
        return false;
    }

    public Account getAccount(String login) {
        return operationsService.request("/getAccountByLogin/?login=" + login, HttpMethod.GET, Account.class);
    }

    public List<Account> searchAccounts(String query) {
        return operationsService.requestList("/searchAccounts/?query=" + query, HttpMethod.GET, Account[].class);
    }

    public Account getAccountByTelephone(String telephone) {
        return operationsService.request("/getAccountByTelephone/?telephone=" + telephone, HttpMethod.GET, Account.class);
    }

    public List<Account> getAllAccount() {
        return operationsService.requestList("/getAllAccount", HttpMethod.GET, Account[].class);
    }

    public Boolean migrateToTariff(String oldTariff, String newTariff) {
        if (tariffController.isTariffExists(oldTariff) && tariffController.isTariffExists(newTariff)) {
            for (Account account : getAllAccount()) {
                if (oldTariff.equals(account.getTariff())) {
                    account.setTariff(newTariff);
                    if (!updateAccount(account)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    public Boolean updateAccount(Account newAccountData) {
        if (isAccountExists(newAccountData.getLogin())) {
            try {
                rabbitMQSender.send(newAccountData, RabbitMQMessageType.UPDATE_ACCOUNT);
                return true;
            } catch (JsonProcessingException e) {
                LOG.error("updateAccount failed", e);
            }

        }
        return false;
    }

    public Boolean deleteAccountByLogin(String login) {
        if (isAccountExists(login)) {
            try {
                rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                return true;
            } catch (Exception e) {
                LOG.error("deleteAccountByLogin failed", e);

            }
        }
        return false;
    }

    public Long getBalance(/*@RequestParam("token")*/ String login) {
        return operationsService.request("/getBalanceByLogin/?login=" + login, HttpMethod.GET, Long.class);
    }

    public Boolean updateBalance(String login, Long amount) {
        try {
            final Account acc = getAccount(login);
            acc.setBalance(amount);
            rabbitMQSender.send(acc, RabbitMQMessageType.ADD_BALANCE);
            return true;
        } catch (Exception e) {
            LOG.error("updateBalance failed", e);
        }
        return false;
    }

    public Boolean addBalance(String login, Long amount) {
        try {
            final Account acc = getAccount(login);
            if ((acc.getBalance() + amount >= 0L)) {
                acc.setBalance(amount);
                rabbitMQSender.send(acc, RabbitMQMessageType.ADD_BALANCE);
                return true;
            }
        } catch (Exception e) {
            LOG.error("addBalance failed", e);
        }
        return false;
    }

    public Internet getInternetBalance(String login) {
        return operationsService.request("/getInternetByLogin/?login=" + login, HttpMethod.GET, Internet.class);
    }

    public Call getCallBalance(String login) {
        return operationsService.request("/getCallByLogin/?login=" + login, HttpMethod.GET, Call.class);
    }

    public Sms getSmsBalance(String login) {
        return operationsService.request("/getSmsByLogin/?login=" + login, HttpMethod.GET, Sms.class);
    }

    public Boolean updateCallBalance(Call call) throws JsonProcessingException {
        rabbitMQSender.send(call, RabbitMQMessageType.UPDATE_CALL);
        return true;
    }
    public Boolean updateSmsBalance(Sms sms) throws JsonProcessingException {
        rabbitMQSender.send(sms, RabbitMQMessageType.UPDATE_SMS);
        return true;
    }
    public Boolean updateInternetBalance(Internet internet) throws JsonProcessingException {
        rabbitMQSender.send(internet, RabbitMQMessageType.UPDATE_INTERNET);
        return true;
    }

    private Long[] calcPrice(long halfPriceBalance, long chargeCount, float halfPriceCost, float fullPriceCost) {
        Long[] result = new Long[4];
        final long halfPriceCount = halfPriceBalance >= chargeCount ? chargeCount : chargeCount - halfPriceBalance;
        final long fullPriceCount = chargeCount - halfPriceCount;

        result[0] = (long) (halfPriceCount * halfPriceCost + fullPriceCount * fullPriceCost);
        result[1] = halfPriceBalance - halfPriceCount;
        result[2] = halfPriceCount;
        result[3] = fullPriceCount;

        return result;
    }

    public boolean callCanBeDone(Account account, long count) {
        if (account != null) {
            final Call callBalance = getCallBalance(account.getLogin());
            final Long[] calcResult = calcPrice(callBalance.getCall_balance(), count, callBalance.getCall_cost(), callBalance.getDefault_call_cost());
            return account.getBalance() >= calcResult[0];
        }
        return false;
    }

    public boolean chargeCall(Account account, long count) {
        if (account != null) {
            final Call callBalance = getCallBalance(account.getLogin());
            final Long[] calcResult = calcPrice(callBalance.getCall_balance(), count, callBalance.getCall_cost(), callBalance.getDefault_call_cost());

            account.setBalance(account.getBalance() - calcResult[0]);
            callBalance.setCall_balance(calcResult[1]);

            try {
                if (calcResult[2] > 0) {
                    rabbitMQSender.send(callBalance, RabbitMQMessageType.CALL_ONE_SECOND);
                }
                if (calcResult[0] > 0) {
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                }
            } catch (JsonProcessingException e) {
                LOG.error("chargeCall failed", e);
            }
            return account.getBalance() > 0;
        }
        return false;
    }


    public boolean smsCanBeSend(Account account, long count) {
        if (account != null) {
            final Sms balance = getSmsBalance(account.getLogin());
            final Long[] calcResult = calcPrice(balance.getSms_balance(), count, balance.getSms_cost(), balance.getDefault_sms_cost());
            return account.getBalance() >= calcResult[0];
        }
        return false;
    }

    public boolean chargeSms(Account account, long count) {
        if (account != null) {
            final Sms balance = getSmsBalance(account.getLogin());
            final Long[] calcResult = calcPrice(balance.getSms_balance(), count, balance.getSms_cost(), balance.getDefault_sms_cost());

            account.setBalance(account.getBalance() - calcResult[0]);
            balance.setSms_balance(calcResult[1]);

            try {
                if (calcResult[2] > 0) {
                    rabbitMQSender.send(balance, RabbitMQMessageType.REQUEST_SMS);
                }
                if (calcResult[0] > 0) {
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                }
            } catch (JsonProcessingException e) {
                LOG.error("chargeCall failed", e);
            }
            return account.getBalance() > 0;
        }
        return false;
    }

    public boolean internetCanBeUsed(Account account, long count) {
        if (account != null) {
            final Internet balance = getInternetBalance(account.getLogin());
            final Long[] calcResult = calcPrice(balance.getInternet_balance(), count, balance.getInternet_cost(), balance.getDefault_internet_cost());
            return account.getBalance() >= calcResult[0];
        }
        return false;
    }

    public boolean chargeInternet(Account account, long count) {
        if (account != null) {
            final Internet balance = getInternetBalance(account.getLogin());
            final Long[] calcResult = calcPrice(balance.getInternet_balance(), count, balance.getInternet_cost(), balance.getDefault_internet_cost());

            account.setBalance(account.getBalance() - calcResult[0]);
            balance.setInternet_balance(calcResult[1]);

            try {
                if (calcResult[2] > 0) {
                    rabbitMQSender.send(balance, RabbitMQMessageType.INTERNET_USE_KILOBYTE);
                }
                if (calcResult[0] > 0) {
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                }
            } catch (JsonProcessingException e) {
                LOG.error("chargeCall failed", e);
            }
            return account.getBalance() > 0;
        }
        return false;
    }


}
