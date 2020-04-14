package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Internet;
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
public class InternetController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;


    /*@GetMapping("useInternetKilobytes")*/
    public Boolean useInternetKilobytes(/*@RequestParam("token")*/ String login,
                                        /*@RequestParam("quantity")*/ Long quantity) {
        return useInternetByQuantity(login, quantity);
    }
    /*@GetMapping("useInternetMegabytes")*/
    public Boolean useInternetMegabytes(/*@RequestParam("token") */String login,
                                        /*@RequestParam("quantity")*/ Long quantity) {
        return useInternetByQuantity(login, quantity*1024); //1000
    }
    /*@PostMapping("createInternet")*/
    public Boolean createInternet(/*@RequestBody*/ Internet internet) throws JsonProcessingException {
        rabbitMQSender.send(internet, RabbitMQMessageType.CREATE_INTERNET);
        return true;
    }

    /*@PutMapping("updateInternet")*/
    public Boolean updateInternet(/*@RequestBody*/ Internet internet) throws JsonProcessingException {
        rabbitMQSender.send(internet, RabbitMQMessageType.UPDATE_INTERNET);
        return true;
    }

    /*@GetMapping("getAllInternet")*/
    public ResponseEntity<List<Internet>> getAllInternet() {
        String url = helpers.getUrlBilling() + "/getAllCall";
        ResponseEntity<List<Internet>> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Internet>>() {
        });
        return responseEntity;
    }

    /*@GetMapping("getInternetByLogin")*/
    public ResponseEntity<Internet> getInternetByLogin(/*@RequestParam("login") */String login) {
        String url = helpers.getUrlBilling() + "/getInternetByLogin/?login=" + login;
        ResponseEntity<Internet> responseEntity = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Internet.class);
        return responseEntity;
    }

    /*@GetMapping("getInternetResourcesByLogin")*/
    public ResponseEntity<Long> getInternetResourcesByLogin(/*@RequestParam("login")*/ String login) {
        return internetBalance(login);
    }

    ///
    public Boolean useInternetByQuantity(String login, Long quantity) {
        try {
            String url = helpers.getUrlBilling() + "/getInternetByLogin/?login=" + login;
            Internet internet = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Internet.class).getBody();

            url = helpers.getUrlBilling() + "/getAccountByLogin/?login=" + login;
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();

            float cost = internet.getInternet_cost();
            float defaultCost = internet.getDefault_internet_cost();
            if (internet.getInternet_balance() <= 0L) {
                if (account.getBalance() - ((long) (defaultCost * quantity)) >= 0) {
                    account.setBalance(-(long) (defaultCost * quantity));
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                } else
                    return false;
            } else {
                // если ресурса по тарифу хватает
                if (internet.getInternet_balance() - quantity >= 0L) {
                    internet.setInternet_balance(quantity);                  // - minutes
                    account.setBalance(-(long) (cost * quantity));    // - points
                    rabbitMQSender.send(internet, RabbitMQMessageType.INTERNET_USE_KILOBYTE);   //TODO
                    rabbitMQSender.send(account, RabbitMQMessageType.ADD_BALANCE);
                    return true;
                }
                // ресурса не хватает
                else {
                    long callLack = quantity - internet.getInternet_balance();
                    long amountForTariff = (long) (cost * internet.getInternet_balance());                 //  нехватка
                    long amountForDefault = (long) (callLack * defaultCost);         //  пересчёт остатка ресурсов
                    if (account.getBalance() - amountForDefault >= 0) {
                        internet.setInternet_balance(internet.getInternet_balance());
                        account.setBalance(-(amountForTariff + amountForDefault));
                        rabbitMQSender.send(internet, RabbitMQMessageType.INTERNET_USE_KILOBYTE);
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
    ///



    //  цена единицы звонка по тарифу
    private ResponseEntity<Float> internetCost(String login) {
        String url = helpers.getUrlBilling() + "/getInternetCostByLogin/?login=" + login;
        ResponseEntity<Float> responseCost = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (responseCost != null) {
            return new ResponseEntity<>(responseCost.getBody(), responseCost.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);

    }

    //  цена единицы звонка без тарифа
    private ResponseEntity<Float> defaultInternetCost(String login) {
        String url = helpers.getUrlBilling() + "/getDefaultInternetCost/?login=" + login;
        ResponseEntity<Float> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
        if (response != null) {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        return new ResponseEntity<>((Float) null, HttpStatus.NOT_FOUND);


    }

    //  количество доступных единиц звонка
    private ResponseEntity<Long> internetBalance(String login) {
        String url = helpers.getUrlBilling() + "/getInternetBalance/?login=" + login;
        ResponseEntity<Long> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        if (response != null) {
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }
        return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);

    }

}
