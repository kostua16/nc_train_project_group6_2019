package com.edunetcracker.billingservice.ProxyValidator.web;

import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyValidator.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.session.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
public class SSP {

    @Autowired
    Checks checks;

    @Autowired
    Helpers helpers;

    @Autowired
    SessionService sessionService;

    //  http://localhost:8102/createA
    /**
     * Создание пользователя и записи по тарифу
     {
     "login": "111",
     "password": "111",
     "name": "1_1",
     "balance": "40000",
     "tariff": "DEFAULT",
     "telephone": "897654321",
     "rang": "USER"
     }

     */
    @PostMapping("createA")
    public ResponseEntity<Boolean> createA(@RequestParam("token") String token,
                                            @RequestBody @NotNull Account newAccount) throws JsonProcessingException {
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.ADMINISTRATOR)) {
            String url = helpers.getUrlProxy() + "createA";
            Boolean response = new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(newAccount, new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    //  http://localhost:8102/createT/?tariff=FOR_SMALL

    /**
     * создание тарифа
     * {
     *   "tariff": {"tariffName":"BIG_GIB"},
     *   "tariffCall": {"Call_cost": "4.5","Call_balance": "100","Default_call_cost": "14.0"},
     *   "tariffInternet": {"Internet_cost": "4.5","Internet_balance": "100","Default_internet_cost": "14.0"},
     *   "tariffSms": {"Sms_cost": "4.5","Sms_balance": "100","Default_sms_cost": "14.0"}
     * }
     */
    @PostMapping("createT")
    public ResponseEntity<Boolean> createT(@RequestParam("token") String token,
                                           @RequestBody Map<String, Map<String, String>> requestB) throws JsonProcessingException {
        String login = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(login).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.ADMINISTRATOR)) {
            String url = helpers.getUrlProxy() + "createT";
            Boolean response = new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(requestB, new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Удаление пользователя и записей
     */
    //  http://localhost:8101/deleteA/?token=!!!&login=111@mail.ru
    @DeleteMapping("deleteA")
    public ResponseEntity<Boolean> deleteA(@RequestParam("token") String token,
                                           @RequestParam("login") String login) throws JsonProcessingException {
        String Mylogin = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(Mylogin).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.ADMINISTRATOR)) {
            String url = helpers.getUrlProxy() + "deleteA/?login=" + login;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.DELETE,  new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    /**
     * Удаление тарифов
     */
    //  http://localhost:8101/deleteT/?token=!!!&login=111@mail.ru
    @DeleteMapping("deleteT")
    public ResponseEntity<Boolean> deleteT(@RequestParam("token") String token,
                                           @RequestParam("name") String name) throws JsonProcessingException {
        String Mylogin = checks.getLoginByTokenAndCheck(token);
        /*
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }*/
        String rang = helpers.getAccount(Mylogin).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.ADMINISTRATOR)) {
            String url = helpers.getUrlProxy() + "deleteT/?name=" + name;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.DELETE,  new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    //  http://localhost:8101/deleteT/?token=!!!&login=111@mail.ru@tariff=BIG_GIB
    @PostMapping("changeT")
    public ResponseEntity<Boolean> changeT(@RequestParam("token") String token,
                           @RequestParam("login") String login,
                           @RequestParam("tariff") String tariff){
        String Mylogin = checks.getLoginByTokenAndCheck(token);
        if(login == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        String rang = helpers.getAccount(Mylogin).getRang();
        if(checks.isAvailableInRanges(rang) && rang.equals(checks.ADMINISTRATOR)) {
            String url = helpers.getUrlProxy() + "changeT/?login=" + login + "&tariff=" + tariff;
            Boolean response = new RestTemplate().exchange(url, HttpMethod.POST,  new HttpEntity(new HttpHeaders()), Boolean.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
