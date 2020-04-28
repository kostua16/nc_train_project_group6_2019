package com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.proxy.CallController;
import com.edunetcracker.billingservice.ProxyProxy.proxy.OperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class Checks {

    @Autowired
    OperationsService operationsService;

    /*************checks**************/
    public Boolean isAccountExists(String accountLogin) {
        return operationsService.request("/getAccountByLogin/?login=" + accountLogin, HttpMethod.GET, Account.class) != null;
    }

    public Boolean isAccountExistsByPhone(String phoneNum) {
        return operationsService.request("/getAccountByTelephone/?telephone=" + phoneNum, HttpMethod.GET, Account.class) != null;
    }

    public Boolean isTariffExists(String tariffName) {
        return operationsService.request("/getTariffByName/?name=" + tariffName, HttpMethod.GET, Tariff.class) != null;
    }



    public Boolean isAddAmountFeasible(String accountLogin, Long amount) {
        final Long balance = operationsService.request("/getBalanceByLogin/?login=" + accountLogin, HttpMethod.GET, Long.class);
        return balance!=null && (balance + amount >= 0L);
    }

}
