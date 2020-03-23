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
import java.util.UUID;

@RestController
@Validated
public class LoginController {


    @Autowired
    private Helpers helpers;

    //  return session "id"
    @GetMapping("login")
    public ResponseEntity<String> login(@RequestParam("login") @NotNull String login) {

        String url = helpers.getUrlProxy() + "/login/?login=" + login;
        try {
            ResponseEntity<String> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<String>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
        }


    }
    /*
    //  return authorization status (true/false)
    @GetMapping("authorize")
    public ResponseEntity<Boolean> authorize (@RequestParam("login") String login,
                                              @RequestParam("password") String password) {
        String url = helpers.getUrlProxy() + "/getBalance/?login=" + login;
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

    */

}
