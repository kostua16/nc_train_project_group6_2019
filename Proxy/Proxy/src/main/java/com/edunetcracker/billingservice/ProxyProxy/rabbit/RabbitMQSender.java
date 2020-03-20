package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${rabbit.rabbitmq.queue2}")
    private String queue;

    //@Value("${rabbit.rabbitmq.exchange}")
    //private String exchange;


    public void send(Object o) throws JsonProcessingException {

        ///
        /*
        String orderJson = objectMapper.writeValueAsString(o);
        Message mes = MessageBuilder
                .withBody(orderJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                //.setMessageId(rabbitMQMessage.messageID)
                //.setHeader("__TypeId__", String.class)
                .build();
        */
        ///
        this.rabbitTemplate.convertAndSend(queue, o);
    }
    public void send(Object o, String messageType) throws JsonProcessingException {

        String orderJson = objectMapper.writeValueAsString(o);
        Message mes = MessageBuilder
                .withBody(orderJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader(messageType, String.class)
                .build();
        this.rabbitTemplate.convertAndSend(queue, mes);
    }

}
