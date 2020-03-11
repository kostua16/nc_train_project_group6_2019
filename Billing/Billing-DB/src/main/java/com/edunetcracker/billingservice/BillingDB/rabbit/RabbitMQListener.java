package com.edunetcracker.billingservice.BillingDB.rabbit;

import com.edunetcracker.billingservice.BillingDB.cipher.Cipher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static com.edunetcracker.billingservice.BillingDB.rabbit.RabbitMQMessageType.GET_ACCOUNT_INFORMATION;

@EnableRabbit
@Service
public class RabbitMQListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RabbitMQMap rabbitMQMap;

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;

    @RabbitListener(queues = "q2")
    public void processQueue2(Message message) {
        //setMessage(message);
        //  ID message
        String s = message.getMessageProperties().getMessageId();
        //  From JSON(1) to String
        Object o = rabbitTemplate.getMessageConverter().fromMessage(message);
        Object deCode = null;
        try {
            //  From String to JSON(2)
            deCode = Cipher.deCode( (String)o);
            //  From JSON to RabbitMQMessage
            RabbitMQMessage responseMessage = objectMapper.readValue( (String)deCode, RabbitMQMessage.class);
            //  Information Message
            String messageId = responseMessage.messageID;
            String type = responseMessage.type;
            List<Object> objectList = responseMessage.body;
            //  Обработчик сообщений
            logic(messageId, type, objectList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void logic(String messageId, String type, List<Object> body) {
        switch (type) {
            case GET_ACCOUNT_INFORMATION: {
                // only send request
                //  String url = "localhost:8888/billing....."
                //new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
                break;
            }
            default: {

                break;
            }
        }
    }
}
