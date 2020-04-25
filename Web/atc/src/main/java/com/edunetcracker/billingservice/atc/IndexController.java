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

    @Value("${app.validator.host}")
    private String validatorHost;

    @Value("${app.validator.port}")
    private String validatorPort;

    @Value("${app.proxy.host}")
    private String proxyHost;

    @Value("${app.proxy.port}")
    private String proxyPort;

    @GetMapping("serversDetails")
    public ResponseEntity<Map<String, String>> getValidatorAddress(){
        Map<String, String> response = new HashMap<>();
        response.put("validator", "http://" + this.validatorHost + ":" + this.validatorPort);
        response.put("proxy", "http://" + this.proxyHost + ":" + this.proxyPort);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping
//    public String index(){
//        return "index";
//    }
}
