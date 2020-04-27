package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.rabbit.RabbitMQListener;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AccountController {

    @Autowired
    IAccountRepository AccountRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    /***************************************************************************************/

    @GetMapping("getAccountByLogin")
    public ResponseEntity<Account> getAccountByLogin(@RequestParam("login") String login) {

        Account account = AccountRepository.findAccountByLogin(login);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @GetMapping("getAccountByTelephone")
    public ResponseEntity<Account> getAccountByTelephone(@RequestParam("telephone") String telephone) {
        LOG.info("getAccountByTelephone1 {}", telephone);
        Account account = AccountRepository.findAccountByTelephone(telephone);
        LOG.info("getAccountByTelephone2 {}", account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("findAccountsByLogin")
    public ResponseEntity<List<Account>> findAccountsByLogin(@RequestParam("login") String login) {

        List<Account> accounts = AccountRepository.findAccountsByLoginContains(login);
        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }
    @GetMapping("findAccountsByTelephone")
    public ResponseEntity<List<Account>> findAccountsByTelephone(@RequestParam("telephone") String telephone) {
        LOG.info("findAccountsByTelephone {}", telephone);
        List<Account> accounts = AccountRepository.findAccountsByTelephoneContains(telephone);
        LOG.info("findAccountsByTelephone {}", accounts);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("searchAccounts")
    public ResponseEntity<List<Account>> searchAccounts(@RequestParam("query") String query) {
        LOG.info("searchAccounts {}", query);
        Set<Account> result = new LinkedHashSet<Account>();
        result.addAll(AccountRepository.findAccountsByLoginContains(query));
        result.addAll(AccountRepository.findAccountsByNameContains(query));
        result.addAll(AccountRepository.findAccountsByTelephoneContains(query));
        LOG.info("searchAccounts {}", result);
        return new ResponseEntity<>(new ArrayList<>(result), HttpStatus.OK);
    }

    @GetMapping("getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount() {

        List<Account> accounts = AccountRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
