package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    IAccountRepository AccountRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    /***************************************************************************************/

    @GetMapping("getAccountByLogin")
    public ResponseEntity<Account> getAccountByLogin(@RequestParam("login") String login) {

        Account account = AccountRepository.findAccountByLogin(login);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @GetMapping("getAccountByTelephone")
    public ResponseEntity<Account> getAccountByTelephone(@RequestParam("telephone") String telephone) {
        System.out.println("getAccountByTelephone1");
        Account account = AccountRepository.findAccountByTelephone(telephone);
        System.out.println("getAccountByTelephone2");
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount() {

        List<Account> accounts = AccountRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

}
