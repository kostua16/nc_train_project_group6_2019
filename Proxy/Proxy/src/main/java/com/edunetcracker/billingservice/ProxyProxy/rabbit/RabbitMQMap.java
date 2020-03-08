package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQMap {

    private Map<String, Object> map = new HashMap<>();

    public void deleteMessage(String id) {
        map.remove(id);
    }

    public Object getMessageFromMap(String id) {
        if(map.containsKey(id)){
            return map.get(id);
        }
        else{
            return null;
        }
    }

    public Object getMessageFromMapAndDelete(String id) {

        Object res = getMessageFromMap(id);
        if(res == null){
            long time = 0;
            while(res == null){
                try {
                    Thread.sleep(100);
                    time += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                res = getMessageFromMap(id);
                if(time>5000){
                    break;
                }
            }
        }
        deleteMessage(id);
        return res;
    }

    public void setMessageToMap(String id, Object message) {
        map.put(id, message);
    }

}
