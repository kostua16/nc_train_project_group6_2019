package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*@RestController*/
@Service
public class BalanceController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @Autowired
    private AccountController accountController;

    /////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping("getBalance")*/
    public ResponseEntity<Long> getBalance(/*@RequestParam("token")*/ String login) {
        try {
            String url = helpers.getUrlBilling() + "/getBalanceByLogin/?login=" + login;
            ResponseEntity responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            return new ResponseEntity<>((Long) responseBalance.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        }
    }

    // add +/- amount
    /*@PutMapping("addToBalance")*/
    public ResponseEntity<Boolean> addToBalance(/*@RequestParam("token")*/ String login,
                                                /*@RequestParam("amount")*/ Long amount) {
        try {
            if (checks.isAddAmountFeasible(login, amount)) {
                Account account = new Account();
                account.setLogin(login);
                account.setBalance(amount);
                rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    }


}
