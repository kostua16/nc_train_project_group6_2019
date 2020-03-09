package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import com.edunetcracker.billingservice.ProxyProxy.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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





    /////////////////////////////////////////////////////////////////////////////
    /**
     * _V2
     * @param cipherMessage - Cipher message
     * @return String MessageID
     */

    public  String sendWithMessageID(String cipherMessage) {
        System.out.println("Send cipher message. Message: " +  cipherMessage);
        try {
        String messageId = UUID.randomUUID().toString();
        //  Convert String to JSON
        //  Because Rabbit understand JSON body but not string body
        //  String => JSON
        String orderJson = objectMapper.writeValueAsString(cipherMessage);
        Message mes = MessageBuilder
                .withBody(orderJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setMessageId(messageId)
                .setHeader("__TypeId__", String.class)
                .build();
        //  Send
        this.rabbitTemplate.convertAndSend(exchange,"q2", mes);
        System.out.println("Message sent. Id = " + messageId);
        return messageId;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //_V2
    /*
    public  String sendWithMessageID(Object message) {
        System.out.println("Send message and his text is " +  ((TestClassData)message).a);
        try {
            String messageId = UUID.randomUUID().toString();
            String orderJson = objectMapper.writeValueAsString(message);
            Message mes = MessageBuilder
                    .withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setMessageId(messageId)
                    .setHeader("__TypeId__", String.class)
                    .build();
            this.rabbitTemplate.convertAndSend(exchange,"q2", mes);
            System.out.println("Send. Id = " + messageId);
            return messageId;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    */
    //_V1
    /*public void send(Object message) {
        rabbitTemplate.convertAndSend(exchange,"q2", message);
        //rabbitTemplate.convertAndSend("q2", message);
        System.out.println("Send to q2. msg = " + (String)message);
    }*/

}
