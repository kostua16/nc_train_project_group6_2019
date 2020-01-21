package com.edunetcracker.billingservice.proxy.validator;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class Logic {

    @GetMapping("/validator/getBalance/{id}")
    public ResponseEntity<Integer> getBalance(@PathVariable String id) {
        //обращение к базе данных
        //final String urlDataBase = "http://localhost:8080/DB/getBalance/" + id;
        //RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<Integer> responseDataBase = restTemplate.exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Integer.class);

        //int result = responseDataBase.getBody();
        int result = Rando.getRandInt(-2,3);
        if(result<0){
            return new ResponseEntity<Integer>((Integer)null, HttpStatus.NOT_FOUND);
        }
        else
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }
}
