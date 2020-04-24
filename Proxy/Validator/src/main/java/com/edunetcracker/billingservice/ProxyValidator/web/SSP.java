package com.edunetcracker.billingservice.ProxyValidator.web;

import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
public class SSP {

    @Autowired
    Checks checks;

    @Autowired
    Helpers helpers;

    @Autowired
    SessionService sessionService;

    /**
     * Стартовая страница
     */
    //  http://localhost:8101/home/?token=!!!
    @GetMapping("home")
    public ResponseEntity<Map<String, String>> home(@RequestParam("token") @NotNull String token){
        String login = checks.getLoginByTokenAndCheck(token);
        System.out.println("V home");
        if(login == null ) {
            System.out.println("null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        //if(checks.isAvailableInRanges(rang) && rang.equals(checks.USER)) {
            System.out.println("2");
            System.out.println("home " + login);
            String url = helpers.getUrlProxy() + "home/?login=" + login;
            Map<String, String> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Map.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        /*}

        System.out.println("3");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);*/

    }
    /**
     * Пополнение баланса
     */
    //  http://localhost:8101/topup/?token=!!!&amount=999
    @GetMapping("topup")
    public ResponseEntity<Boolean> topup (@RequestParam("token") @NotNull String token,
                                          @RequestParam("amount") @NotNull Long amount ) {
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.USER)) {
            String url = helpers.getUrlProxy() + "topup/?login=" + login + "&amount=" + amount;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Показ всех доступных тарифов вместе с текущим для пользователя
     */
    //  http://localhost:8101/showtariff/?token=!!!
    @GetMapping("showtariff")
    public ResponseEntity<Map<String, Object>> showtariff(@RequestParam("token") @NotNull String token){
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.USER)) {
            String url = helpers.getUrlProxy() + "showtariff/?login=" + login;
            Map<String, Object> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Map.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Смена тарифа
     */
    //  http://localhost:8101/choicetariff/?token=!!!&tariff=FOR_SMALL
    @GetMapping("choicetariff")
    public ResponseEntity<Boolean> choicetariff(@RequestParam("token") String token,
                                                @RequestParam("tariff") String tariff){
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.USER)) {
            String url = helpers.getUrlProxy() + "choicetariff/?login=" + login + "&tariff=" + tariff;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping("getTelephone")
    public ResponseEntity<String> getTelephone(@RequestParam("token") String token) {
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.USER)) {
            String url = helpers.getUrlProxy() + "getTelephone/?login=" + login;
            String response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
