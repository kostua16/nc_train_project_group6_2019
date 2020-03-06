package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import com.rabbitmq.client.RpcClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@EnableRabbit
@Service
public class RabbitMQListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //private AmqpTemplate rabbitTemplate;

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;

    public Object listen(){
        Object o = rabbitTemplate.receiveAndConvert("q2");
        System.out.println("Listen to q2. msg = " + (String)o);
        return o;
    }

    //Message message = new Message();
    //message.getMessageProperties().getCorrelationId();

    /*
    @RabbitListener(queues = "q1")
    public Object processQueue1(Object message) {
        System.out.println("Listen q1. msg = " + (String)message);
        return message;
    }
    @RabbitListener(queues = "q2")
    public Object processQueue2(Object message) {
        System.out.println("Listen q2. msg = " + (String)message);  //without unpacking
        return message;
    }
    */
}
