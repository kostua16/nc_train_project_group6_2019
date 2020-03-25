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
public class InternetController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    // +проверки на использование внепакетных услуг
    // + входит ли услуга в список доступных
    @GetMapping("useInternet")
    public ResponseEntity<Boolean> useInternet(@RequestParam("login") String login/*,
                                               @RequestParam("byte") Long bytes*/) {  //!!
        try {
            ////
            String url;

            url = helpers.getUrlProxy() + "/internetBalance/?login=" + login;
            Long internetBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();

            url = helpers.getUrlProxy() + "/internetCost/?login=" + login;
            Float internetCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

            Long lackResources = checks.lackResources(internetBalance, 1024L, internetCost);  //  resources = bytes

            // если закончились средства тарифа
            if (lackResources > 0L) {
                url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
                Long accountBalance = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class).getBody();
                ////
                url = helpers.getUrlProxy() + "/defaultInternetCost/?login=" + login;
                Float defaultInternetCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class).getBody();

                Long debt = checks.lackBalance(lackResources, accountBalance, defaultInternetCost);
                //  если деньги есть
                // возможно стоит послать два сообщения кролику на использование
                // доступных ресурсов и использование чистого баланса клиента
                if (debt == 0L) {
                    //TODO RABBIT
                    rabbitMQSender.send(login, RabbitMQMessageType.INTERNET_USE_KILOBYTE);
                    return new ResponseEntity<>(true, HttpStatus.OK);
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
    @GetMapping("internetCost")
    public ResponseEntity<Float> internetCost(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/internetCost/?login=" + login;
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
    @GetMapping("defaultInternetCost")
    public ResponseEntity<Float> defaultInternetCost(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/defaultInternetCost/?login=" + login;
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
    @GetMapping("internetBalance")
    public ResponseEntity<Long> internetBalance(@RequestParam("login") String login) {
        try {
            //TODO GET
            String url = helpers.getUrlBilling() + "/internetBalance/?login=" + login;
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
