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
public class SmsController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    // +проверки на использование внепакетных услуг
    // + входит ли услуга в список доступных
    @GetMapping("requestSMS")
    public ResponseEntity<Boolean> requestSMS(@RequestParam("token") String token) {
        try {
            if (sessionService.inSession(token)) {
                String login = sessionService.getLogin(token).getLogin();

                if (checks.isAccountExists(login)) {
                    //String url = helpers.getUrlProxy() + "/smsBalance/?login=" + login;
                    Long smsBalance = smsBalance(login).getBody();//new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();

                    //url = helpers.getUrlProxy() + "/smsCost/?login=" + login;
                    Float smsCost = smsCost(login).getBody(); //new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

                    Long lackResources = checks.lackResources(smsBalance, smsCost.longValue());  //  resources = 1

                    // если закончились средства тарифа
                    if (lackResources > 0L) {
                        String url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
                        Long accountBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();
                        ////
                        //url = helpers.getUrlProxy() + "/defaultSMSCost/?login=" + login;
                        Float defaultInternetCost = defaultSMSCost(login).getBody();//new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

                        Long debt = checks.lackBalance(lackResources, accountBalance, defaultInternetCost);
                        //  если деньги есть
                        // возможно стоит послать два сообщения кролику на использование
                        // доступных ресурсов и использование чистого баланса клиента
                        if (debt == 0L) {
                            rabbitMQSender.send(login, RabbitMQMessageType.INTERNET_USE_KILOBYTE);
                            return new ResponseEntity<>(true, HttpStatus.OK);
                        }

                    }
                }
            }
            //rabbitMQSender.send(login, RabbitMQMessageType.STOP_CALL);
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Boolean) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //  цена байта по тарифу
    @GetMapping("smsCost")
    public ResponseEntity<Float> smsCost(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/smsCost/?login=" + login;
            ResponseEntity<Float> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            if (response != null) {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
            return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Float) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //  цена байта без тарифа
    @GetMapping("defaultSMSCost")
    public ResponseEntity<Float> defaultSMSCost(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/defaultSMSCost/?login=" + login;
            ResponseEntity<Float> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            if (response != null) {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
            return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Float) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    //  количество доступного трафика
    @GetMapping("smsBalance")
    public ResponseEntity<Long> smsBalance(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/smsBalance/?login=" + login;
            ResponseEntity<Long> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            if (response != null) {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Long) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
