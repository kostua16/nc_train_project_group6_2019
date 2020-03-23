package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    // request => response
    private ResponseEntity returnResponseFromUrl(String url) {
        try {
            ResponseEntity<String> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test text in proxy. Text = " + str;
    }

    @GetMapping("/isConnect")
    public ResponseEntity<String> isConnect() {
        String url = helpers.getUrlBilling() + "/isConnect";
        ResponseEntity<String> stringResponseEntity;
        String response = "Proxy response. ";
        try {
            stringResponseEntity = returnResponseFromUrl(url);
            return new ResponseEntity<String>((response + stringResponseEntity.getBody()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(("Only " + response), HttpStatus.OK);
        }

    }

    /*
    @GetMapping("/getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam("id") Long id) {

        String url = getUrlBilling() + "/getAccount?id=" + id;
        ResponseEntity<Account> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

        if(response.equals(null)){
            return new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND);
        }
        else return response;
    }
    //?
    @PostMapping("/postAccount")
    public ResponseEntity<?> postAccount(@RequestBody Account account) throws JsonProcessingException {
        rabbitMQSender.send(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("getTTT")
    public Account getTTT() {

        Account account = new Account();
        account.number = 10l;
        account.block = 1000l;
        account.balance = 1000000l;
        String url = "http://localhost:8102/qqq";
        try {
            ResponseEntity<Account> response = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(account, new HttpHeaders()), Account.class);

            Account responseAcc = response.getBody();
            return responseAcc;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    @PutMapping("qqq")
    public Account qqqGetTTT(@RequestBody Account account) {

        Account acc = account;
        return acc;
    }
     */
}
