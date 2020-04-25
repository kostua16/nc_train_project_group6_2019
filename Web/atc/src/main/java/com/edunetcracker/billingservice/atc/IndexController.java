package com.edunetcracker.billingservice.atc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${app.validator.url}")
    private String validatorUrl;

    @Value("${app.proxy.url}")
    private String proxyUrl;

    @GetMapping("serversDetails")
    public ResponseEntity<Map<String, String>> getValidatorAddress(){
        Map<String, String> response = new HashMap<>();
        response.put("validator", validatorUrl);
        response.put("proxy", proxyUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping
//    public String index(){
//        return "index";
//    }
}
