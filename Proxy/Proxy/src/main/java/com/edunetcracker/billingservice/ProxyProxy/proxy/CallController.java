package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Call;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/*@RestController*/
@Service
public class CallController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

   /* @GetMapping("callToMinutes")*/
    public Boolean callToMinutes(/*@RequestParam("token")*/ String login) {
        try {
            String url = helpers.getUrlBilling() + "/getCallByLogin/?login=" + login;
            Call call = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Call.class).getBody();

            url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();

            float cost = call.getCall_cost();
            float defaultCost = call.getDefault_call_cost();
            if (call.getCall_balance() <= 0L) {
                if (account.getBalance() - ((long) (defaultCost * 60)) >= 0) {
                    account.setBalance(-(long) (defaultCost * 60));   // - points
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                } else
                    return false;
            } else {
                // если ресурса по тарифу хватает
                if (call.getCall_balance() - 60 >= 0L) {
                    call.setCall_balance(60L);                  // - minutes
                    account.setBalance(-(long) (cost * 60));    // - points
                    rabbitMQSender.send(call, RabbitMQMessageType.CALL_ONE_MINUTE);
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                }
                // ресурса не хватает
                else {
                    long callLack = 60 - call.getCall_balance();
                    long amountForTariff = (long) (cost * call.getCall_balance());                 //  нехватка
                    long amountForDefault = (long) (callLack * defaultCost);         //  пересчёт остатка ресурсов
                    if (account.getBalance() - amountForDefault >= 0) {
                        call.setCall_balance(call.getCall_balance());
                        account.setBalance(-(amountForTariff + amountForDefault));
                        rabbitMQSender.send(call, RabbitMQMessageType.CALL_ONE_MINUTE);
                        rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /*@GetMapping("callToSecond")*/
    public Boolean callToSecond(/*@RequestParam("token") */String login)  {
        try {
            String url = "http://localhost:8202/getCallByLogin/?login=" + login;
            Call call = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Call.class).getBody();

            url = "http://localhost:8202/getAccountByLogin/?login=" + login;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();

            float cost = call.getCall_cost();
            float defaultCost = call.getDefault_call_cost();
            if (call.getCall_balance() <= 0L) {
                if (account.getBalance() - ((long) (defaultCost)) >= 0) {
                    account.setBalance(-(long) (defaultCost));   // - points
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                } else
                    return false;
            } else {
                // если ресурса по тарифу хватает
                if (call.getCall_balance() - 1 >= 0L) {
                    call.setCall_balance(1L);                  // - minutes
                    account.setBalance(-(long) (cost));         // - points
                    rabbitMQSender.send(call, RabbitMQMessageType.CALL_ONE_SECOND);
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                }
                // ресурса не хватает
                else {
                    if (account.getBalance() - defaultCost >= 0) {
                        account.setBalance(-(long) defaultCost);         // - points
                        rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*@PostMapping("createCall")*/
    public Boolean createCall(/*@RequestBody*/ Call call) throws JsonProcessingException {
        rabbitMQSender.send(call, RabbitMQMessageType.CREATE_CALL);

        return true;
    }

    /*@PutMapping("updateCall")*/
    public Boolean updateCall(/*@RequestBody*/ Call call) throws JsonProcessingException {
        rabbitMQSender.send(call, RabbitMQMessageType.UPDATE_CALL);
        return true;
    }

    /*@GetMapping("getAllCall")*/
    public ResponseEntity<List<Call>> getAllCalls() {
        String url = helpers.getUrlBilling() + "/getAllCall";
        ResponseEntity<List<Call>> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Call>>() {
        });
        return responseEntity;
    }

    /*@GetMapping("getCallByLogin")*/
    public ResponseEntity<Call> getCallByLogin(/*@RequestParam("login")*/ String login) {
        String url = helpers.getUrlBilling() + "/getCallByLogin/?login=" + login;
        ResponseEntity<Call> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Call.class);
        return responseEntity;
    }

    /*@GetMapping("getCallResourcesByLogin")*/
    public ResponseEntity<Long> getCallResourcesByLogin(/*@RequestParam("login") */String login) {
        return callBalance(login);
    }

    //  цена единицы звонка по тарифу
    private ResponseEntity<Float> callCost(String login) {
        String url = helpers.getUrlBilling() + "/getCallCostByLogin/?login=" + login;
        ResponseEntity<Float> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (responseCallCost != null) {
            return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);

    }

    //  цена единицы звонка без тарифа
    private ResponseEntity<Float> defaultCallCost(String login) {
        String url = helpers.getUrlBilling() + "/getDefaultCallCost/?login=" + login;
        ResponseEntity<Float> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (responseCallCost != null) {
            return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);


    }

    //  количество доступных единиц звонка
    private ResponseEntity<Long> callBalance(String login) {
        String url = helpers.getUrlBilling() + "/getCallBalance/?login=" + login;
        ResponseEntity<Long> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        if (responseCallCost != null) {
            return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
        }
        return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);

    }

}
