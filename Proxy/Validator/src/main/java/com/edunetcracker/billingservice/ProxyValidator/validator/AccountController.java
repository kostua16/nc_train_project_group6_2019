package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.helpers.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class AccountController {

    @Autowired
    private Helpers helpers;

    @GetMapping("getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/getAccount/?login=" + login;
        try {
            ResponseEntity<Account> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            return new ResponseEntity<Account>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("createAccount")
    public ResponseEntity<Boolean> createAccount(@Valid @RequestBody Account account) {
        String url = helpers.getUrlProxy() + "/createAccount";
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(account, new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateAccount")
    public ResponseEntity<Boolean> updateAccount(@RequestParam("login") @NotNull String login,
                                                 @Valid @RequestBody Account newAccountData) {
        String url = helpers.getUrlProxy() + "/updateAccount/?login=" + login;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(newAccountData, new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("deleteAccount")
    public ResponseEntity<Boolean> deleteAccount(@Valid @RequestParam("login") String login) {

        String url = helpers.getUrlProxy() + "/deleteAccount/?login=" + login;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity(new HttpHeaders()), Boolean.class);
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
