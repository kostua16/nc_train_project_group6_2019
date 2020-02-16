package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping("/proxy/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test proxy response. " + str;
    }

    @GetMapping("/proxy/getBalance/")
    public ResponseEntity<Long> getBalance(@RequestParam("id") Long id) {

        long result =  new Rando().getRandInt(-2L,3L);
        if(result<0){
            return new ResponseEntity<Long>((Long)null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
        }
        else
            return new ResponseEntity<Long>(result, HttpStatus.OK);
    }

    @GetMapping("/proxy/getBalanceWithRabbitMessage/")
    public ResponseEntity<Long> getBalanceWithRabbitMessage(@RequestParam("id") Long id, @RequestParam("message") Boolean optionMessage) {

        long result =  new Rando().getRandInt(-2L,3L);
        if(optionMessage.equals(true)){
            rabbitMQSender.send("This is test message! Rand = " + result);
        }
        if(result<0){
            return new ResponseEntity<Long>((Long)null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
        }else
            return new ResponseEntity<Long>(result, HttpStatus.OK);
    }

}
