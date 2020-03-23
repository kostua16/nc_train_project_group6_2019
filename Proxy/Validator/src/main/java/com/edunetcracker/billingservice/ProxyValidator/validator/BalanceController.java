package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.helpers.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

@RestController
@Validated
public class BalanceController {

    @Autowired
    private Helpers helpers;


    @GetMapping("getBalance")
    public ResponseEntity<Long> getBalance(@RequestParam("login") @NotNull String login /*,
                                              @RequestParam("password") String password*/) {
        String url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
        try {
            ResponseEntity<Long> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            return new ResponseEntity<Long>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Long>((Long) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Long>((Long) null, HttpStatus.NOT_FOUND);
        }


    }

    // add +/- amount
    @PutMapping("addToBalance")
    public ResponseEntity<Boolean> addToBalance(@RequestParam("login") @NotNull String login,
                                                @RequestParam("amount") @NotNull Long amount) {
        String url = helpers.getUrlProxy() + "/addToBalance/?login=" + login + "&amount=" + amount;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity(new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }

    }


}
