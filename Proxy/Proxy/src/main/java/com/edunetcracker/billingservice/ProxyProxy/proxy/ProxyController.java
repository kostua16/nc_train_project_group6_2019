package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProxyController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    // request => response
    private ResponseEntity returnResponseFromUrl(String url) {
        try {
            ResponseEntity<String> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("creAcc")
    public String creact(@RequestParam("login") String login) throws JsonProcessingException {
        Account account = new Account();
        account.setLogin(login);

        rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);

        return "Good";
    }
    @GetMapping("upAcc")
    public String upAcc(@RequestParam("login") String login, @RequestParam("password") String password) throws JsonProcessingException {
        String url = "http://localhost:8202/getAccount/?login="+login;
        ResponseEntity<Account> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
        Account account = responseEntity.getBody();
        //account.setLogin("update_"+login);
        account.setPassword("new_"+password);
        rabbitMQSender.send(account, RabbitMQMessageType.UPDATE_ACCOUNT);

        return "Good";
    }
    @GetMapping("delAcc")
    public String delAcc(@RequestParam("login") String login) throws JsonProcessingException {

        rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);

        return "Good";
    }

    @GetMapping("getAcc")
    public ResponseEntity<Account> getac(@RequestParam("login") String login) {
        String url = "http://localhost:8202/getAccount/?login="+login;
        ResponseEntity<Account> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
        return responseEntity;
    }

    @GetMapping("getAllAcc")
    public ResponseEntity<List<Account>> getAllAcc() {
        String url = "http://localhost:8202/getAllAccount";
        ResponseEntity<List<Account>> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Account>>() {});

        return responseEntity;
    }



    @GetMapping("/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test text in proxy. Text = " + str;
    }

    @GetMapping("/isConnect")
    public ResponseEntity<String> isConnect() {
        String url = helpers.getUrlBilling() + "/isConnect";
        ResponseEntity<String> stringResponseEntity;
        String response = "Proxy response. ";
        try {
            stringResponseEntity = returnResponseFromUrl(url);
            return new ResponseEntity<String>((response + stringResponseEntity.getBody()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(("Only " + response), HttpStatus.OK);
        }

    }

    /*
    @GetMapping("/getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("id") Long id) {

        String url = getUrlBilling() + "/getAccount?id=" + id;
        ResponseEntity<Account> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

        if(response.equals(null)){
            return new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND);
        }
        else return response;
    }
    //?
    @PostMapping("/postAccount")
    public ResponseEntity<?> postAccount(@RequestBody Account account) throws JsonProcessingException {
        rabbitMQSender.send(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("getTTT")
    public Account getTTT() {

        Account account = new Account();
        account.number = 10l;
        account.block = 1000l;
        account.balance = 1000000l;
        String url = "http://localhost:8102/qqq";
        try {
            ResponseEntity<Account> response = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(account, new HttpHeaders()), Account.class);

            Account responseAcc = response.getBody();
            return responseAcc;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    @PutMapping("qqq")
    public Account qqqGetTTT(@RequestBody Account account) {

        Account acc = account;
        return acc;
    }
     */
}
