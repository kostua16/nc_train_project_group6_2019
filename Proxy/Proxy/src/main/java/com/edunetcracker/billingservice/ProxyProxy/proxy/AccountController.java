package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<Account> getAccount(@RequestParam("login") String login /*,
                                              @RequestParam("password") String password*/) {
        try {
            //существует или нет
            Boolean accountExists = checks.isAccountExists(login);
            //да - получить, нет - ошибка
            if (accountExists != null) {
                if (accountExists) {
                    //String url = getUrlBilling() + "/getAccount/?login=" + login + "&password=" + password;
                    //TODO GET
                    String url = helpers.getUrlBilling() + "/getAccount/?login=" + login;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

                    return new ResponseEntity<>((Account) responseAccount.getBody(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>((Account) null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>((Account) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Account) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("createAccount")
    public ResponseEntity<Boolean> createAccount(@RequestBody Account account) {
        try {
            //существует или нет
            Boolean accountExists = checks.isAccountExists(account.getLogin());

            //да - ошибка, нет - создать
            if (accountExists != null) {
                if (accountExists) {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

                } else {
                    //TODO RABBIT
                    rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                    return new ResponseEntity<>(true, HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateAccount")
    public ResponseEntity<Boolean> updateAccount(@RequestParam("login") String login,
                                                 @RequestBody Account newAccountData) {
        try {
            Boolean accountExists = checks.isAccountExists(login);

            newAccountData.setLogin(login);// на всякий случай

            // если существует, то обновить
            if (accountExists) {
                //String url = helpers.getUrlBilling() + "/updateAccount";
                //ResponseEntity<Boolean> isExist = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(newAccountData, new HttpHeaders()), Boolean.class);
                //return new ResponseEntity<>(isExist.getBody(), isExist.getStatusCode());
                //TODO RABBIT
                rabbitMQSender.send(newAccountData, RabbitMQMessageType.UPDATE_ACCOUNT);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteAccount")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam("login") String login) {

        try {
            Boolean accountExists = checks.isAccountExists(login);

            // если существует, то удалить
            if (accountExists) {
//                String url = helpers.getUrlBilling() + "/deleteAccount";
//                ResponseEntity<Boolean> isExist = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity(new HttpHeaders()), Boolean.class);
//                return new ResponseEntity<>(isExist.getBody(), isExist.getStatusCode());
                //TODO RABBIT
                rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
