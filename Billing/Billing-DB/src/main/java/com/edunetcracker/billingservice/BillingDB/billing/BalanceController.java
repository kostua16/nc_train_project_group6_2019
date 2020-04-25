package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    IAccountRepository AccountRepository = null;

    @Autowired
    ObjectMapper objectMapper;
    Logger LOG = LoggerFactory.getLogger(BalanceController.class);

    /***************************************************************************************/

    @GetMapping("getBalanceByLogin")
    public ResponseEntity<Long> getBalanceByLogin(@RequestParam("login") String login) {

        Account account = AccountRepository.findAccountByLogin(login);
        LOG.info("acc b = " + account.getBalance());
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }
}
