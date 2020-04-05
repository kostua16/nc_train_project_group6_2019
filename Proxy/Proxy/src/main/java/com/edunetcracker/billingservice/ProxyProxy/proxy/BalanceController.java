package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalanceController {


    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getBalance")
    public ResponseEntity<Long> getBalance(@RequestParam("login") String login) {

        String url = "http://localhost:8202/getBalanceByLogin/?login=" + login;
        ResponseEntity responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        return new ResponseEntity<>((Long) responseBalance.getBody(), HttpStatus.OK);

    }

    // add +/- amount
    @PutMapping("addToBalance")
    public ResponseEntity<Boolean> addToBalance(@RequestParam("login") String login,
                                                @RequestParam("amount") Long amount) throws JsonProcessingException {

        Account account = new Account();
        account.setLogin(login);
        account.setBalance(amount);
        rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
        return new ResponseEntity<>(true, HttpStatus.OK);


    }


}
