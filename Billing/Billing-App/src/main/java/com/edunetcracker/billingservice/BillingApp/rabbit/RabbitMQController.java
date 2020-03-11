package com.edunetcracker.billingservice.BillingApp.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RabbitMQController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    // Возможно это должно быть в Billing DB
    @GetMapping("")
    public void getAccountInformation(@RequestParam("id") String stringId) {

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
    }

}
