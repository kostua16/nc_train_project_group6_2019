package com.edunetcracker.billingservice.atc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${app.validator.host}")
    private String validatorHost;

    @Value("${app.validator.port}")
    private String validatorPort;

    @GetMapping("validatorAddress")
    public String getValidatorAddress(){
        return "http://" + this.validatorHost + ":" + this.validatorPort;
    }

//    @GetMapping
//    public String index(){
//        return "index";
//    }
}
