package com.edunetcracker.billingservice.BillingDB.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@EnableRabbit
@Service
public class RabbitMQListener {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RabbitListener(queues = "q1")
    public void processQueue1(String message) {
        System.out.println("Listen q1. msg = " + message);
    }
    @RabbitListener(queues = "q2")
    public void processQueue2(String message) {
        System.out.println("Listen q2. msg = " + message);  //without unpacking
    }
}
