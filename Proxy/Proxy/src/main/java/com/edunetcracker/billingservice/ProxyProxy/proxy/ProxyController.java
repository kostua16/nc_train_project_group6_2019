package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Value("${app.billing.host}")
    private String hostBilling = "localhost";

    @Value("${app.billing.port}")
    private String portBilling = "8080";

    private String getUrlBilling (){
        return "http://"+ hostBilling + ":" + portBilling;
    }

    // request => response
    private ResponseEntity returnResponseFromUrl (String url) {
        try {
            ResponseEntity<String> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity<>((String)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>((String)null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test text in proxy. Text = " + str;
    }

    @GetMapping("/isConnect")
    public ResponseEntity<String> isConnect() {
        String url = getUrlBilling() + "/isConnect";
        ResponseEntity<String> stringResponseEntity;
        String response = "Proxy response. ";
        try {
            stringResponseEntity = returnResponseFromUrl(url);
            return new ResponseEntity<String>((response + stringResponseEntity.getBody()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<String>(("Only " + response), HttpStatus.OK);
        }

    }

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
}
