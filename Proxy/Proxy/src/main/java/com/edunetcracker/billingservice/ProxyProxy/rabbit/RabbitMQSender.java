package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbit.rabbitmq.routingkey}")
    private String routing;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routing, message);
        System.out.println("Send msg = " + message);
    }

}
