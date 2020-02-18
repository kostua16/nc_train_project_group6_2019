package com.edunetcracker.billingservice.BillingDB.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    //@Value("${rabbit.rabbitmq.exchange}")
    //private String exchange;

    public void send(Object message) {
        rabbitTemplate.convertAndSend("q1", message);
        System.out.println("Send to q1. msg = " + message);
    }

}
