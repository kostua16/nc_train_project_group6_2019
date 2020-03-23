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
public class CallController {

    @Autowired
    private Helpers helpers;

    @GetMapping("callToMinutes")
    public ResponseEntity<Boolean> callToMinutes(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/callToMinutes/?login=" + login;
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

    //  цена единицы звонка по тарифу
    @GetMapping("callCost")
    public ResponseEntity<Float> callCost(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/callCost/?login=" + login;
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

    //  цена единицы звонка без тарифа
    @GetMapping("defaultCallCost")
    public ResponseEntity<Float> defaultCallCost(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/defaultCallCost/?login=" + login;
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

    //  количество доступных единиц звонка
    @GetMapping("callBalance")
    public ResponseEntity<Long> callBalance(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/callBalance/?login=" + login;
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
