package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import java.util.List;

public class RabbitMQMessage {
    public String messageID;
    public String type;
    public List<Object> body;

    public RabbitMQMessage(String messageID, String type, List<Object> body){
        this.messageID = messageID;
        this.type = type;
        this.body = body;
    }
}
