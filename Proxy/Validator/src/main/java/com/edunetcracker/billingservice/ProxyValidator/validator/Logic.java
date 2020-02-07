package com.edunetcracker.billingservice.ProxyValidator.validator;


import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
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
import java.util.Map;


@RestController
@Validated
public class Logic {

    @GetMapping("/validator/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test validator response. " + str;
    }

    @GetMapping("/validator/getBalance/")
    public ResponseEntity<Long> getBalance(@RequestParam ("id") Long id) {
        //обращение к proxy
        final String urlDataBase = "http://localhost:8102/proxy/getBalance/"+"?id="+ + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Long> responsePoxy = null;
        try {
            responsePoxy = restTemplate.exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        } catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>((Long)null, HttpStatus.NOT_FOUND);
        }
            //return responsePoxy;
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());
    }
    @GetMapping("/validator/getBalanceWithValidation/")
    public ResponseEntity<Long> getBalanceWithValidation(@RequestParam ("id") @Min(1) @Max(10) @NotNull Long id,
                                                         @RequestParam("message") Boolean optionMessage) {
    //обращение к proxy
    final String urlDataBase = "http://localhost:8102/proxy/getBalanceWithRabbitMessage/"+"?id="+ + id + "&message="+optionMessage;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Long> responsePoxy = null;
        try {
            responsePoxy = restTemplate.exchange(urlDataBase, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Long.class);
        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>((Long) null, HttpStatus.NOT_FOUND);  //BAD_REQUEST
        }
        return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
