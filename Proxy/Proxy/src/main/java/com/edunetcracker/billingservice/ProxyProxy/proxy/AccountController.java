package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("login") String login) throws JsonProcessingException {
        String url = "http://localhost:8202/getAccountByLogin/?login="+login;
        ResponseEntity<Account> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
        return responseEntity;
    }

    @GetMapping("getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount() throws JsonProcessingException {
        String url = "http://localhost:8202/getAllAccount";
        ResponseEntity<List<Account>> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Account>>() {});
        return responseEntity;
    }

    @PostMapping("createAccount")
    public Boolean createAccount(@RequestParam("login") String login
                                                /*@RequestBody Account account*/) throws JsonProcessingException {
        Account account = new Account();
        account.setLogin(login);
        account.setBalance(1000L);
        rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);

        return true;
    }

    @PutMapping("updateAccount")
    public String updateAccount(@RequestParam("login") String login,
                                @RequestParam("password") String password
                                /*@RequestBody Account newAccountData*/) throws JsonProcessingException {
        String url = "http://localhost:8202/getAccount/?login="+login;
        ResponseEntity<Account> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
        Account account = responseEntity.getBody();
        account.setPassword("new_"+password);
        rabbitMQSender.send(account, RabbitMQMessageType.UPDATE_ACCOUNT);

        return "Good";

    }

    @DeleteMapping("deleteAccount")
    public String deleteAccount(@RequestParam("login") String login) throws JsonProcessingException {

        rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);

        return "Good";
    }

}
