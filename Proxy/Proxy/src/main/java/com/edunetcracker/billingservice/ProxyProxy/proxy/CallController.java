package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @GetMapping("callToMinutes")
    public ResponseEntity<Boolean> callToMinutes(@RequestParam("login") String login) {
        try {
            ////
            String url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
            Long accountBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();
            ////
            url = helpers.getUrlProxy() + "/callCost/?login=" + login;
            Long accountCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();

            // есди звонить может
            if(checks.canAccountCall(accountBalance, accountCallCost)) {
                rabbitMQSender.send(login, RabbitMQMessageType.CALL);
                return new ResponseEntity<>(true , HttpStatus.OK);
            }
            rabbitMQSender.send(login, RabbitMQMessageType.STOP_CALL);
            return new ResponseEntity<>(false , HttpStatus.FORBIDDEN);

        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Boolean) null , HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
    @GetMapping("callCost")
    public ResponseEntity<Long> callCost(@RequestParam("login") String login) {
        try {
            String url = helpers.getUrlBilling() + "/callCost/?login=" + login;
            ResponseEntity<Long> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            if(responseCallCost != null) {
                return new ResponseEntity<>(responseCallCost.getBody() , responseCallCost.getStatusCode());
            }
            return new ResponseEntity<>((Long) null , HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
