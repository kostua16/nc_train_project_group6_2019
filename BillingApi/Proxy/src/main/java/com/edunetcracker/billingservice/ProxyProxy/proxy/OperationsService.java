package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class OperationsService {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    Logger LOG = LoggerFactory.getLogger(OperationsService.class);


    @Value("${app.billing.url}")
    private String billingUrl = "http://localhost:8202";

    /*************helpers**************/
    public String getUrlBilling() {
        return billingUrl;
    }




    public  <T> T request(String url, HttpMethod method, Class<T> requiredType){
        try {
            final ResponseEntity<T> result = new RestTemplate().exchange(getUrlBilling() + url, method, new HttpEntity(new HttpHeaders()), requiredType);
            if(HttpStatus.NOT_FOUND.equals(result.getStatusCode())){
                LOG.info("request [{}, {}, {}] => Null", url, method, requiredType);
                return null;
            } else {
                T resultBody = result.getBody();
                LOG.info("request [{}, {}, {}] => {}", url, method, requiredType, resultBody);
                return resultBody;
            }
        } catch (RestClientException e){
            LOG.error(String.format("request [%s, %s, %s] => Fail", url, method, requiredType), e);
            return null;
        }
    }
    public  <T> List<T> requestList(String url, HttpMethod method, Class<T[]> requiredType){
        try {
            final ResponseEntity<T[]> result = new RestTemplate().exchange(getUrlBilling() + url, method, new HttpEntity(new HttpHeaders()), requiredType);
            if(HttpStatus.NOT_FOUND.equals(result.getStatusCode())){
                LOG.info("request [{}, {}, {}] => Null", url, method, requiredType);
                return null;
            } else {
                List<T> resultBody;
                if(result.getBody()!=null){
                    resultBody = new LinkedList<T>(Arrays.asList(result.getBody()));
                } else {
                    resultBody = new ArrayList<>();
                }
                LOG.info("request [{}, {}, {}] => {}", url, method, requiredType, resultBody);
                return resultBody;
            }
        } catch (RestClientException e){
            LOG.error(String.format("request [%s, %s, %s] => Fail", url, method, requiredType), e);
            return null;
        }
    }

    public  <T> ResponseEntity<T> requestEntity(String url, HttpMethod method, Class<T> requiredType){
        return notNull(request(url, method, requiredType));
    }

    public  <T> ResponseEntity<List<T>> requestListEntity(String url, HttpMethod method, Class<T[]> requiredType){
        return notNull(requestList(url, method, requiredType));
    }

    public  <T> ResponseEntity<T> notNull(T item){
        if(item!=null){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
