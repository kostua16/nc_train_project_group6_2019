package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //private AmqpTemplate rabbitTemplate;


    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;


    public void send(Object message) {
        rabbitTemplate.convertAndSend(exchange,"q2", message);
        //rabbitTemplate.convertAndSend("q2", message);
        System.out.println("Send to q2. msg = " + (String)message);
    }


}
