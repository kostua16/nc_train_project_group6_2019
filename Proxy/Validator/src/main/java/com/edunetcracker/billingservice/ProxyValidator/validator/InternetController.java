package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.helpers.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

@RestController
@Validated
public class InternetController {

    @Autowired
    private Helpers helpers;

    // +проверки на использование внепакетных услуг
    // + входит ли услуга в список доступных
    @GetMapping("useInternet")
    public ResponseEntity<Boolean> useInternet(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/useInternet/?login=" + login;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }


    }

    //  цена байта по тарифу
    @GetMapping("internetCost")
    public ResponseEntity<Float> internetCost(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/internetCost/?login=" + login;
        try {
            ResponseEntity<Float> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            return new ResponseEntity<Float>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Float>((Float) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Float>((Float) null, HttpStatus.NOT_FOUND);
        }

    }

    //  цена байта без тарифа
    @GetMapping("defaultInternetCost")
    public ResponseEntity<Float> defaultInternetCost(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/defaultInternetCost/?login=" + login;
        try {
            ResponseEntity<Float> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Float.class);
            return new ResponseEntity<Float>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Float>((Float) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Float>((Float) null, HttpStatus.NOT_FOUND);
        }


    }

    //  количество доступного трафика
    @GetMapping("internetBalance")
    public ResponseEntity<Long> internetBalance(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/internetBalance/?login=" + login;
        try {
            ResponseEntity<Long> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
            return new ResponseEntity<Long>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Long>((Long) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Long>((Long) null, HttpStatus.NOT_FOUND);
        }

    }

}
