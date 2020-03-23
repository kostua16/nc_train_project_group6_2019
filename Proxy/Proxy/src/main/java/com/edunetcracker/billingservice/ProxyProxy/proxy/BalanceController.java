package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
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
    public ResponseEntity<Long> getBalance(@RequestParam("login") String login /*,
                                              @RequestParam("password") String password*/) {
        try {
            //существует или нет
            Boolean accountExists = checks.isAccountExists(login);
            //да - получить, нет - ошибка
            if (accountExists != null) {
                if (accountExists) {
                    String url = helpers.getUrlBilling() + "/getBalance/?login=" + login;
                    ResponseEntity responseBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);

                    return new ResponseEntity<>((Long) responseBalance.getBody(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>((Long) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // add +/- amount
    @PutMapping("addToBalance")
    public ResponseEntity<Boolean> addToBalance(@RequestParam("login") String login,
                                                 @RequestParam("amount") Long amount) {
        try {
            Boolean accountExists = checks.isAccountExists(login);

            // если существует, то добавить
            if (accountExists) {
                // если можно +/-
                if (checks.isAddAmountFeasible(login, amount)) {
                    rabbitMQSender.send(amount, RabbitMQMessageType.ADD_BALANCE);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
