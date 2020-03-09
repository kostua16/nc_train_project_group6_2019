package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import com.edunetcracker.billingservice.ProxyProxy.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.RpcClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@EnableRabbit
@Service
public class RabbitMQListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //private AmqpTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RabbitMQMap rabbitMQMap;

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;

    //

    /**
     * _V2
     * Listen all messages;
     */
    @RabbitListener(queues = "q2")
    public void processQueue2(Message message) {
        setMessage(message);
    }


    public void setMessage(Message message){
        //  get ID and convert
        String s = message.getMessageProperties().getMessageId();
        //  Decoding message
        //  JSON => Object(String)
        Object o = rabbitTemplate.getMessageConverter().fromMessage(message);
        rabbitMQMap.setMessageToMap(s, o);
        System.out.println("Message: " + s + " send to map");
    }

    public RabbitMQMessage listen(String id){
        System.out.println("Message listen. Id = " + id);
        //  Get message with delayed
        //  If it is not available, wait 5 sec
        Object o = rabbitMQMap.getMessageFromMapAndDelete(id);
        if(o == null) {
            System.out.println("Message: " + id + " not found");
        }

        //  Decoding String to JSON,                    //1
        //  and receive Message class from JSON         //2
        //  String => JSON => Class
        Object deCode = null;                           //1
        try {
            deCode = Cipher.deCode( (String)o);         //1
            RabbitMQMessage responseMessage = objectMapper.readValue( (String)deCode, RabbitMQMessage.class); //2
            return  responseMessage;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }




    //////////////////////////////////////////////////////

    public Object listenWithMessageID(String id){
        System.out.println("Message listen. Id = " + id);
        //  Get message with delayed
        //  If it is not available, wait 5 sec
        Object o = rabbitMQMap.getMessageFromMapAndDelete(id);
        if(o == null) {
            System.out.println("Message: " + id + " not found");
        }
        return o;
    }

    //_V1
    /*public Object listen(){
        Object o = rabbitTemplate.receiveAndConvert("q2");
        System.out.println("Listen to q2. msg = " + (String)o);
        return o;
    }*/
}
