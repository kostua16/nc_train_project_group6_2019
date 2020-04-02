package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AccountController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("token") String token ) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();

                if (checks.isAccountExists(login)) {
                    //TODO GET
                    String url = helpers.getUrlBilling() + "/getAccount/?login=" + login;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

                    return new ResponseEntity<>((Account) responseAccount.getBody(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>((Account) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Account) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getAccountForName")
    public ResponseEntity<Account> getAccountForName(@RequestParam("token") String token,
                                                     @RequestParam("login") String login ) {
        try {
            if(sessionService.inSession(token)) {
                if (checks.isAccountExists(login)) {
                    //TODO GET
                    String url = helpers.getUrlBilling() + "/getAccount/?login=" + login;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

                    return new ResponseEntity<>((Account) responseAccount.getBody(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>((Account) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Account) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("createAccount")
    public ResponseEntity<Boolean> createAccount(@RequestParam("token") String token,
                                                 @RequestBody Account account) {
        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    //TODO RABBIT
                    rabbitMQSender.send(account, RabbitMQMessageType.CREATE_ACCOUNT);
                    return new ResponseEntity<>(true, HttpStatus.CREATED);

                }
            }
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateAccount")
    public ResponseEntity<Boolean> updateAccount(@RequestParam("token") String token,
                                                 @RequestBody Account newAccountData) {
        try {
            if(sessionService.inSession(token)) {
                String login = newAccountData.getLogin(); //sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    //String url = helpers.getUrlBilling() + "/updateAccount";
                    //ResponseEntity<Boolean> isExist = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(newAccountData, new HttpHeaders()), Boolean.class);
                    //return new ResponseEntity<>(isExist.getBody(), isExist.getStatusCode());
                    //TODO RABBIT
                    rabbitMQSender.send(newAccountData, RabbitMQMessageType.UPDATE_ACCOUNT);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteAccount")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam("token") String token) {

        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
//                String url = helpers.getUrlBilling() + "/deleteAccount";
//                ResponseEntity<Boolean> isExist = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity(new HttpHeaders()), Boolean.class);
//                return new ResponseEntity<>(isExist.getBody(), isExist.getStatusCode());
                    //TODO RABBIT
                    rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAccountForName")
    public ResponseEntity<Boolean> deleteAccountForName(@RequestParam("token") String token,
                                                        @RequestParam("login") String login ) {
        try {
            if(sessionService.inSession(token)) {
                if (checks.isAccountExists(login)) {
                    //TODO RABBIT
                    rabbitMQSender.send(login, RabbitMQMessageType.DELETE_ACCOUNT);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
