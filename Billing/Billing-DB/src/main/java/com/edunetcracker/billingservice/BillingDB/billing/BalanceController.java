package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /***************************************************************************************/

    @GetMapping("getBalanceByLogin")
    public ResponseEntity<Long> getBalanceByLogin(@RequestParam("login") String login) {

        Account account = AccountRepository.findAccountByLogin(login);
        System.out.println("acc b = " + account.getBalance());
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }
}
