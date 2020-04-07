package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Sms;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class SmsController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @GetMapping("requestSms")
    public Boolean requestSms(@RequestParam("token") String token,
                              @RequestParam("quantity") Long quantity) {
        return requestSmsByQuantity(token, quantity);
    }

    @PostMapping("createSms")
    public Boolean createSms(@RequestBody Sms sms) throws JsonProcessingException {
        rabbitMQSender.send(sms, RabbitMQMessageType.CREATE_SMS);

        return true;
    }

    @PutMapping("updateSms")
    public Boolean updateSms(@RequestBody Sms sms) throws JsonProcessingException {
        rabbitMQSender.send(sms, RabbitMQMessageType.UPDATE_SMS);
        return true;
    }

    @GetMapping("getAllSms")
    public ResponseEntity<List<Sms>> getAllSms() {
        String url = helpers.getUrlBilling() + "/getAllSms";
        ResponseEntity<List<Sms>> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Sms>>() {
        });
        return responseEntity;
    }

    @GetMapping("getSmsByLogin")
    public ResponseEntity<Sms> getSmsByLogin(@RequestParam("login") String login) {
        String url = helpers.getUrlBilling() + "/getSmsByLogin/?login=" + login;
        ResponseEntity<Sms> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Sms.class);
        return responseEntity;
    }

    @GetMapping("getSmsResourcesByLogin")
    public ResponseEntity<Long> getSmsResourcesByLogin(@RequestParam("login") String login) {
        return smsBalance(login);
    }

    ///
    public Boolean requestSmsByQuantity(String token, Long quantity) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {

                    String url = helpers.getUrlBilling() + "/getSmsByLogin/?login=" + login;
                    Sms internet = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Sms.class).getBody();

                    url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
                    Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();

                    float cost = internet.getSms_cost();
                    float defaultCost = internet.getDefault_sms_cost();
                    if (internet.getSms_balance() <= 0L) {
                        if (account.getBalance() - ((long) (defaultCost * quantity)) >= 0) {
                            account.setBalance(-(long) (defaultCost * quantity));
                            rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                            return true;
                        } else
                            return false;
                    } else {
                        // если ресурса по тарифу хватает
                        if (internet.getSms_balance() - quantity >= 0L) {
                            internet.setSms_balance(quantity);                  // - minutes
                            account.setBalance(-(long) (cost * quantity));    // - points
                            rabbitMQSender.send(internet, RabbitMQMessageType.REQUEST_SMS);   //TODO
                            rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                            return true;
                        }
                        // ресурса не хватает
                        else {
                            long callLack = quantity - internet.getSms_balance();
                            long amountForTariff = (long) (cost * internet.getSms_balance());                 //  нехватка
                            long amountForDefault = (long) (callLack * defaultCost);         //  пересчёт остатка ресурсов
                            if (account.getBalance() - amountForDefault >= 0) {
                                internet.setSms_balance(internet.getSms_balance());
                                account.setBalance(-(amountForTariff + amountForDefault));
                                rabbitMQSender.send(internet, RabbitMQMessageType.REQUEST_SMS);
                                rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                                return true;
                            }
                        }
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    ///


    //  цена единицы звонка по тарифу
    public ResponseEntity<Float> smsCost(String login) {
        String url = helpers.getUrlBilling() + "/getSmsCostByLogin/?login=" + login;
        ResponseEntity<Float> responseCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (responseCost != null) {
            return new ResponseEntity<>(responseCost.getBody(), responseCost.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);

    }

    //  цена единицы звонка без тарифа
    public ResponseEntity<Float> defaultSmsCost(String login) {
        String url = helpers.getUrlBilling() + "/getDefaultSmsCost/?login=" + login;
        ResponseEntity<Float> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (response != null) {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);


    }

    //  количество доступных единиц звонка
    public ResponseEntity<Long> smsBalance(String login) {
        String url = helpers.getUrlBilling() + "/getSmsBalance/?login=" + login;
        ResponseEntity<Long> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        if (response != null) {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);

    }
}
