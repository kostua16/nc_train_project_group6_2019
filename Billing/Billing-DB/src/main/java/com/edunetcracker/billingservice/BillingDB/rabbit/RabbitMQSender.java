package com.edunetcracker.billingservice.BillingDB.rabbit;

import com.edunetcracker.billingservice.BillingDB.cipher.Cipher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;


    public void send(RabbitMQMessage rabbitMQMessage) {
        try {
            //  Create JSON (String) from Class,
            //  and serializing a string
            //  Class => StringJSON => String
            String cipherMessage = Cipher.enCode(objectMapper.writeValueAsString(rabbitMQMessage));
            //  Convert String to JSON
            //  Because Rabbit understand JSON body but not string body
            //  String => JSON
            String orderJson = objectMapper.writeValueAsString(cipherMessage);
            Message mes = MessageBuilder
                    .withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setMessageId(rabbitMQMessage.messageID)
                    .setHeader("__TypeId__", String.class)
                    .build();
            //  Send
            this.rabbitTemplate.convertAndSend(exchange,"q2", mes);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}