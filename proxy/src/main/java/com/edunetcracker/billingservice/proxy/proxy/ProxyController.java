package com.edunetcracker.billingservice.proxy.proxy;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    @GetMapping("/proxy/getBalance/{id}")
    public ResponseEntity<Integer> getBalance(@PathVariable String id) {
        //обращение к validator
        final String urlDataBase = "http://localhost:8080/validator/getBalance/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> responseDataBase = restTemplate.exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Integer.class);
        return responseDataBase;
    }

}
