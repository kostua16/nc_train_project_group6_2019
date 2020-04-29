package com.edunetcracker.billingservice.ProxyValidator.web;

import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyValidator.filter.SimpleLoggingFilter;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger LOG = LoggerFactory.getLogger(SSP.class);

    /**
     * Стартовая страница
     */
    //  http://localhost:8101/home/?token=!!!
    @GetMapping("home")
    public ResponseEntity<Map<String, String>> home(@RequestParam("token") @NotNull String token){
        String login = checks.getLoginByTokenAndCheck(token);
        LOG.info("V home");
        if(login == null ) {
            LOG.info("null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            LOG.info("2");
            LOG.info("home " + login);
            String url = helpers.getUrlProxy() + "/home/?login=" + login;
            Map<String, String> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Map.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        LOG.info("3");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Пополнение баланса
     */
    //  http://localhost:8101/topup/?token=!!!&amount=999
    @GetMapping("topup")
    public ResponseEntity<Boolean> topup (@RequestParam("token") @NotNull String token,
                                          @RequestParam("amount") @NotNull Long amount ) {
        String login = checks.getLoginByTokenAndCheck(token);
        LOG.info("topup({}, {}) => login = {}", token, amount, login);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        LOG.info("topup({}, {}) => rang = {}", token, amount, rang);
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            String url = helpers.getUrlProxy() + "/topup/?login=" + login + "&amount=" + amount;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            LOG.info("topup({}, {}) => response = {}", token, amount, response);
            if(response!=null){
                if(response){
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
                }
            }


        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @GetMapping("topUpByTelephone")
    public ResponseEntity<Boolean> topUpByTelephone (@RequestParam("token") @NotNull String token,
                                                     @RequestParam("telephone") @NotNull Long telephone,
                                                     @RequestParam("amount") @NotNull Long amount ) {
        String login = checks.getLoginByTokenAndCheck(token);
        LOG.info("topUpByTelephone({}, {}, {}) => login = {}", token, telephone, amount, login);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        LOG.info("topUpByTelephone({}, {}, {}) => rang = {}", token, telephone, amount, rang);
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            String url = helpers.getUrlProxy() + "/topUpByTelephone/?telephone=" + telephone + "&amount=" + amount;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            LOG.info("topUpByTelephone({}, {}, {}) => response = {}", token, telephone, amount, response);
            if(response!=null){
                if(response){
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
                }
            }


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
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            String url = helpers.getUrlProxy() + "/showtariff/?login=" + login;
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
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            String url = helpers.getUrlProxy() + "/choicetariff/?login=" + login + "&tariff=" + tariff;
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
        if(Checks.USER.equals(rang) || Checks.ADMINISTRATOR.equals(rang)) {
            String url = helpers.getUrlProxy() + "/getTelephone/?login=" + login;
            String response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
