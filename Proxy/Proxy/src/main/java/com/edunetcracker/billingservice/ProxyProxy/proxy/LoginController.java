package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
public class LoginController {

    //@Autowired
    //private RabbitMQSender rabbitMQSender;

    //@Autowired
    //private SessionService sessionService;

    @Autowired
    private Helpers helpers;

    //  return session "id"
    @GetMapping("login")
    public ResponseEntity<String> login(@RequestParam("login") String login/*,
                                        @RequestParam("password") String password*/) {
        try {
            String url = helpers.getUrlProxy() + "/getAccount/?login=" + login;// + "&password=" + password;
            //TODO GET
            Account account = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class).getBody();
            //  если account есть
            if (account != null) {
                String session = UUID.randomUUID().toString();
                return new ResponseEntity<>(session, HttpStatus.OK);

               /* String session = sessionService.newSession(new Login(login, password));

                //да - если пользователь ещё не вошёл
                if(session != null) {
                    return new ResponseEntity<>(session, HttpStatus.OK);
                }
                return new ResponseEntity<>((String) null, HttpStatus.INTERNAL_SERVER_ERROR);*/
            } else {
                return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((String) null, HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }


    }
    /*
    //  return authorization status (true/false)
    @GetMapping("authorize")
    public ResponseEntity<Boolean> authorize (@RequestParam("login") String login,
                                              @RequestParam("password") String password) {
        try{
            String url = helpers.getUrlBilling() + "/authorize/?login=" + login + "&password=" + password;
            //  должен вернуть
            Boolean is = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Boolean.class).getBody();

            if (is != null) {
                return new ResponseEntity<>(is, HttpStatus.OK); //200
            } else {
                return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR); //404
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    */

}
