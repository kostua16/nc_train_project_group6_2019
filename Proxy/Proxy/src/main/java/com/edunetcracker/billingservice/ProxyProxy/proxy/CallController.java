package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallController {


    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @GetMapping("callToMinutes")
    public ResponseEntity<Boolean> callToMinutes(@RequestParam("token") String token) {
        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {

                    //String url = helpers.getUrlProxy() + "/callBalance/?login=" + login;
                    Long accountResourceBalance = callBalance(login).getBody();//new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();

                    //url = helpers.getUrlProxy() + "/callCost/?login=" + login;
                    Float accountCallCost = callCost(login).getBody();//new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

                    Long lackResources = checks.lackResources(accountResourceBalance, 60L, accountCallCost);

                    // если закончились средства тарифа
                    if (lackResources > 0L) {
                        String url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
                        Long accountBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();
                        ////
                        url = helpers.getUrlProxy() + "/defaultCallCost/?login=" + login;
                        Float defaultCallCost = defaultCallCost(login).getBody();//new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

                        Long debt = checks.lackBalance(lackResources, accountBalance, defaultCallCost);
                        //  если деньги есть
                        // возможно стоит послать два сообщения кролику на использование
                        // доступных ресурсов и использование чистого баланса клиента
                        if (debt == 0L) {
                            //TODO RABBIT
                            rabbitMQSender.send(login, RabbitMQMessageType.CALL_ONE_MINUTE);
                            return new ResponseEntity<>(true, HttpStatus.OK);
                        }
                        else {
                            rabbitMQSender.send(login, RabbitMQMessageType.STOP_CALL);
                            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
                        }

                    }
                    rabbitMQSender.send(login, RabbitMQMessageType.CALL_ONE_MINUTE);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Boolean) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stopCall")
    public ResponseEntity<Boolean> stopCall(@RequestParam("token") String token) {
        try {
            if(sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();
                if (checks.isAccountExists(login)) {
                    //TODO RABBIT
                    rabbitMQSender.send(login, RabbitMQMessageType.STOP_CALL);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>((Boolean) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    //  цена единицы звонка по тарифу
    //@GetMapping("callCost")
    public ResponseEntity<Float> callCost(/*@RequestParam("login")*/ String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/callCost/?login=" + login;
            ResponseEntity<Float> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            if (responseCallCost != null) {
                return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
            }
            return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Float) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //  цена единицы звонка без тарифа
    //@GetMapping("defaultCallCost")
    public ResponseEntity<Float> defaultCallCost(/*@RequestParam("login")*/ String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/defaultCallCost/?login=" + login;
            ResponseEntity<Float> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            if (responseCallCost != null) {
                return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
            }
            return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Float) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //  количество доступных единиц звонка
    //@GetMapping("callBalance")
    public ResponseEntity<Long> callBalance(/*@RequestParam("login")*/ String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/callBalance/?login=" + login;
            ResponseEntity<Long> responseCallCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            if (responseCallCost != null) {
                return new ResponseEntity<>(responseCallCost.getBody(), responseCallCost.getStatusCode());
            }
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
