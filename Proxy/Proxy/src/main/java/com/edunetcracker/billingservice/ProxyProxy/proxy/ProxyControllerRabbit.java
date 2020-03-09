package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQListener;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessage;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ProxyControllerRabbit {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    RabbitMQListener rabbitMQListener;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/proxy/getAccountInformation/")
    public ResponseEntity<String> getAccountInformation(@RequestParam("id") String stringId) throws JsonProcessingException {

        //create elements of message
        String messageId = UUID.randomUUID().toString();
        String type = RabbitMQMessageType.GET_ACCOUNT_INFORMATION;
        Long bodyAccountId = Long.parseLong(stringId);
        List<Object> objectList = new ArrayList<>();
        objectList.add(bodyAccountId);

        //create message
        RabbitMQMessage rMessage = new RabbitMQMessage(messageId, type, objectList);

        //send message
        rabbitMQSender.send(rMessage);
        //listen message
        RabbitMQMessage responseMessage = rabbitMQListener.listen(messageId);

        if(responseMessage != null){

            //String responseType = responseMessage.type;
            //String responseAccountJSON = (String)(responseMessage.body.get(0));
            Account account = new Account();
            account.number = bodyAccountId;
            account.balance = 10000l;
            account.block = 0l;
            return new ResponseEntity<String>(objectMapper.writeValueAsString(account), HttpStatus.OK);
            /*
            if(responseType == RabbitMQMessageType.GET_ACCOUNT_INFORMATION) {
                return new ResponseEntity<String>(responseAccountJSON, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>((String)null, HttpStatus.NOT_FOUND);
            }
            */
        }
        // если всё плохо
        else {
            return new ResponseEntity<String>((String)null, HttpStatus.BAD_REQUEST);
        }
    }
}
