package com.edunetcracker.billingservice.ProxyValidator.validator;


import com.edunetcracker.billingservice.ProxyValidator.cipher.Cipher;
import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import com.edunetcracker.billingservice.ProxyValidator.entity.TestClassData;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;


@RestController
@Validated
public class TestValidatorController {

    @Value("${app.proxy_proxy.host}")
    String hostProxy = "localhost";

    @Value("${app.proxy_proxy.port}")
    String portProxy = "8102";

    private String getUrlProxy (){
        return "http://"+ hostProxy + ":" + portProxy;
    }

    private  ResponseEntity returnResponseFromUrl (String url) {
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

    @GetMapping("/validator/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test validator response. " + str;
    }

    @GetMapping("/validator/getBalance/")
    public ResponseEntity<Long> getBalance(@RequestParam ("id") Long id) {
        //обращение к proxy
        final String urlDataBase = getUrlProxy() + "/proxy/getBalance/"+"?id="+ + id;
        return returnResponseFromUrl(urlDataBase);
    }
    @GetMapping("/validator/getBalanceWithValidation/")
    public ResponseEntity<Long> getBalanceWithValidation(@RequestParam ("id") @Min(1) @Max(10) @NotNull Long id,
                                                         @RequestParam("message") Boolean optionMessage) {
        //обращение к proxy
        final String urlDataBase = getUrlProxy() + "/proxy/getBalanceWithRabbitMessage/"+"?id="+ + id + "&message="+optionMessage;
        return returnResponseFromUrl(urlDataBase);
    }

    /**
     * http://localhost:8101/validator/getMessageWithTestClassData/?message=ThisIsAMessage
     * @param message - Message
     * @return String message
     */
    @GetMapping("/validator/getMessageWithTestClassData/")
    public ResponseEntity<String> getMessageWithTestClassData(@RequestParam("message") String message) {
        //обращение к proxy
        TestClassData testClassData = new TestClassData();
        testClassData.a = message;
        ObjectMapper objectMapper = new ObjectMapper();
        String urlDataBase;
        try {
            //  Create JSON (String) from Class,
            //  and serializing a string
            //  Class => StringJSON => String
            String code = Cipher.enCode(objectMapper.writeValueAsString(testClassData));
            //  Connection Message to URL Param
            urlDataBase = getUrlProxy() + "/proxy/getMessageWithTestClassData/"+"?object="+code;
            //  Getting a response
            ResponseEntity<TestClassData> responsePoxy = null;
            try {

                responsePoxy = new RestTemplate().exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TestClassData.class);

                return new ResponseEntity<String>(responsePoxy.getBody().a, responsePoxy.getStatusCode());

            } catch (HttpClientErrorException.NotFound e){

                return new ResponseEntity<String>((String)null, HttpStatus.NOT_FOUND);
            }
            catch (HttpClientErrorException e){
                e.printStackTrace();
                return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
            }

        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class) //@ExceptionHandler({1.class, 2.class, 3.class}) //для нескольких исключений
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
