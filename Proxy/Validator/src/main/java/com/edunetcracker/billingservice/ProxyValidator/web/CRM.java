package com.edunetcracker.billingservice.ProxyValidator.web;

import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CRM {

    @Autowired
    Checks checks;

    @Autowired
    Helpers helpers;

    //  http://localhost:8101/home/?token=!!!
    @GetMapping("home")
    public ResponseEntity<Map<String, String>> home(@RequestParam("token") @NotNull String token){
        String login = checks.getLoginByToken(token);
        System.out.println("home");
        if(login == null) {
            System.out.println("null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        System.out.println("home "+login);
        String url = helpers.getUrlProxy() + "home/?login=" + login;
        Map<String, String> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Map.class).getBody();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //  http://localhost:8101/topup/?token=!!!&amount=999
    @GetMapping("topup")
    public ResponseEntity<Boolean> topup (@RequestParam("token") @NotNull String token,
                                          @RequestParam("amount") @NotNull Long amount ) {
        String login = checks.getLoginByToken(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String url = helpers.getUrlProxy() + "topup/?login=" + login + "&amount=" + amount;
        Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //  http://localhost:8101/showtariff/?token=!!!
    @GetMapping("showtariff")
    public ResponseEntity<Map<String, Map<String, String>>> showtariff(@RequestParam("token") @NotNull String token){
        String login = checks.getLoginByToken(token);
        if(login == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String url = helpers.getUrlProxy() + "showtariff/?login=" + login;
        Map<String, Map<String, String>> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Map.class).getBody();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //  http://localhost:8101/choicetariff/?token=!!!&tariff=FOR_SMALL
    @GetMapping("choicetariff")
    public ResponseEntity<Boolean> choicetariff(@RequestParam("token") String token,
                                                @RequestParam("tariff") String tariff){
        String login = checks.getLoginByToken(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String url = helpers.getUrlProxy() + "choicetariff/?login=" + login + "&tariff=" + tariff;
        Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
