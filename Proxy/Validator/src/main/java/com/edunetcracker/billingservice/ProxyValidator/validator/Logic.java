package com.edunetcracker.billingservice.ProxyValidator.validator;


import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
public class Logic {

    @GetMapping("/validator/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test validator response. " + str;
    }

    @GetMapping("/validator/getBalance/")
    public ResponseEntity<Long> getBalance(@RequestParam ("id") Long id) {
        //обращение к proxy
        final String urlDataBase = "http://localhost:8102/proxy/getBalance/"+"?id="+ + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Long> responsePoxy = null;
        try {
            responsePoxy = restTemplate.exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        } catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
            //return responsePoxy;
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());
    }
}
