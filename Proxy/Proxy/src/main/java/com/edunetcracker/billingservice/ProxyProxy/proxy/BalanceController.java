package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalanceController {


    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getBalance")
    public ResponseEntity<Long> getBalance(@RequestParam("token") String token) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    //TODO GET
                    String url = helpers.getUrlBilling() + "/getBalanceByLogin/?login=" + login;
                    ResponseEntity responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);

                    return new ResponseEntity<>((Long) responseBalance.getBody(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAnotherBalance")
    public ResponseEntity<Long> getAnotherBalance(@RequestParam("token") String token,
                                                  @RequestParam("login") String login) {
        try {
            if (sessionService.inSession(token)) {
                if (checks.isAccountExists(login)) {
                    String url = helpers.getUrlBilling() + "/getBalanceByLogin/?login=" + login;
                    ResponseEntity responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);

                    return new ResponseEntity<>((Long) responseBalance.getBody(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        }


    }

    // add +/- amount
    @PutMapping("addToBalance")
    public ResponseEntity<Boolean> addToBalance(@RequestParam("token") String token,
                                                @RequestParam("amount") Long amount) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    // если можно +/-
                    if (checks.isAddAmountFeasible(login, amount)) {
                        Account account = new Account();
                        account.setLogin(login);
                        account.setBalance(amount);
                        rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                        return new ResponseEntity<>(true, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }


}
