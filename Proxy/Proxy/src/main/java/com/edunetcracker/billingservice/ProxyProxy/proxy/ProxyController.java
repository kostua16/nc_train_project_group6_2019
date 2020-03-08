package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyProxy.entity.TestClassData;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQListener;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
public class ProxyController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    RabbitMQListener rabbitMQListener;


    @GetMapping("/proxy/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test proxy response. " + str;
    }

    @GetMapping("/proxy/getBalance/")
    public ResponseEntity<Long> getBalance(@RequestParam("id") Long id) {

        long result =  new Rando().getRandInt(-2L,3L);
        if(result<0){
            return new ResponseEntity<Long>((Long)null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
        }
        else
            return new ResponseEntity<Long>(result, HttpStatus.OK);
    }

    @GetMapping("/proxy/getBalanceWithRabbitMessage/")
    public ResponseEntity<Long> getBalanceWithRabbitMessage(@RequestParam("id") Long id, @RequestParam("message") Boolean optionMessage) {

        long result =  new Rando().getRandInt(-2L,3L);

        if(optionMessage.equals(true)){

            //////////////////////////////////////////
            /*
            rabbitMQSender.send(Long.toString(result));

            Object o = rabbitMQListener.listen();

            if(o!= null){
                System.out.println("Я получить! " +(String)o);
            }
            */
            /////////////////////////////////////////////
            String mesId = rabbitMQSender.sendWithMessageID(Long.toString(result));
            if(mesId!=null){
                System.out.println("Я получить ID! " + mesId);
            }

            Object o = rabbitMQListener.listen(mesId);

            if(o!= null){
                System.out.println("Я получить ответ! " +(String)o);

                long mesResult = Long.parseLong((String)o);
                if(mesResult<0){
                    return new ResponseEntity<Long>((Long)null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
                }else
                    return new ResponseEntity<Long>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Long>((Long)null, HttpStatus.BAD_REQUEST);
            }


        }


        if(result<0){
            return new ResponseEntity<Long>((Long)null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
        }else
            return new ResponseEntity<Long>(result, HttpStatus.OK);
    }

    @GetMapping("/proxy/getMessageWithTestClassData/")
    public ResponseEntity<TestClassData> getMessageWithTestClassData(@RequestParam("object") Object message) throws JsonProcessingException {

        System.out.println("Getting message: " + (String)message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            /*  Only For Me
            //  Decoding String to JSON,        //1
            //  and receive Message class       //2
            //  String => JSON => Class
            Object deC = Cipher.deCode((String)message);                                    //1
            TestClassData ooo  = objectMapper.readValue((String)deC, TestClassData.class);  //2
            System.out.println("Send TestClassData.a = " + ooo.a);
            */

            //  Send to Rabbit (initial message without decode)
            //  Send "34fsagj=="
            String messageId = rabbitMQSender.sendWithMessageID((String)message);

            if (messageId != null) {
                System.out.println("Message ID: " + messageId);
                //  Listen Rabbit
                //  Listen "34fsagj=="
                Object responseObjectMessage = rabbitMQListener.listen(messageId);

                if(responseObjectMessage!=null) {

                    //  Decoding String to JSON,                    //1
                    //  and receive Message class from JSON         //2
                    //  String => JSON => Class
                    Object deCode = Cipher.deCode( (String)responseObjectMessage);                                  //1
                    TestClassData responseObject  = objectMapper.readValue( (String)deCode, TestClassData.class);   //2

                    if (responseObject != null) {
                        System.out.println("Message: " + responseObject.a);
                        return new ResponseEntity<TestClassData>(responseObject, HttpStatus.OK);

                    } else {
                        return new ResponseEntity<TestClassData>((TestClassData) null, HttpStatus.NOT_FOUND); //NOT_FOUND //BAD_REQUEST //INTERNAL_SERVER_ERROR
                    }
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<TestClassData>((TestClassData) null, HttpStatus.BAD_REQUEST);
    }

}
