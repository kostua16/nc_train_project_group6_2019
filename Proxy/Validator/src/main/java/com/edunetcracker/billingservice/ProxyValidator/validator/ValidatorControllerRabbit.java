package com.edunetcracker.billingservice.ProxyValidator.validator;


import com.edunetcracker.billingservice.ProxyValidator.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyValidator.entity.TestClassData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@Validated
public class ValidatorControllerRabbit {

    @Value("${app.proxy_proxy.host}")
    String hostProxy = "localhost";

    @Value("${app.proxy_proxy.port}")
    String portProxy = "8102";

    private String getUrlProxy (){
        return "http://"+ hostProxy + ":" + portProxy;
    }

    private ResponseEntity returnResponseFromUrl (String url) {
        ResponseEntity<Long> responsePoxy = null;
        try {
            responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        } catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());
    }

    /**
     * http://localhost:8101/validator/getAccountInformation/?id=100
     * @param message - Message
     * @return String message
     */
    @GetMapping("/validator/getAccountInformation/")
    public ResponseEntity<String> getAccountInformation(@RequestParam("id") Long message) {

        //заглушка
        if(false)
            return new ResponseEntity<String>("In Progress", HttpStatus.OK);
        //обращение к proxy
        //отправка JSON как ответ
        String urlDataBase = getUrlProxy() + "/proxy/getAccountInformation/"+"?id="+message.toString();

        ResponseEntity<String> responsePoxy = null;


        try {
            responsePoxy = new RestTemplate().exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<String>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e){
            e.printStackTrace();
            return new ResponseEntity<String>((String)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            e.printStackTrace();
            return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class) //@ExceptionHandler({1.class, 2.class, 3.class}) //для нескольких исключений
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
