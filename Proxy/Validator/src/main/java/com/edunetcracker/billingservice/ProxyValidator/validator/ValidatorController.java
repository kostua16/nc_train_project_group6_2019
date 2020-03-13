package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.entity.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
//@RequestMapping("/proxy/")
@Validated
public class ValidatorController {
    @Value("${app.proxy_proxy.host}")
    private String hostProxy = "localhost";

    @Value("${app.proxy_proxy.port}")
    private String portProxy = "8102";

    private String getUrlProxy (){
        return "http://"+ hostProxy + ":" + portProxy;
    }

    // request => response
    private ResponseEntity returnResponseFromUrl (String url) {
        try {
            ResponseEntity<String> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
            return new ResponseEntity<>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity<>((String)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            return new ResponseEntity<>((String)null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test/")
    public String getTest(@RequestParam("str") String str) {
        return "Test text in validator. Text = " + str;
    }
    @GetMapping("/isConnect")
    public String isConnect() {
        String url = getUrlProxy() + "/isConnect";
        ResponseEntity<String> stringResponseEntity = returnResponseFromUrl(url);
        String response = "Validator response. ";
        if(stringResponseEntity!=null){
           return  response + stringResponseEntity.getBody();
        }
        else return "Only " + response;
    }

    @GetMapping("/getAccount")
    public ResponseEntity<Account> getAccount(@RequestParam ("id") @Min(1) @NotNull Long id) {

        String url = getUrlProxy() + "/getAccount?id=" + id;
        try {
            ResponseEntity<Account> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
            return new ResponseEntity<Account>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e){
            e.printStackTrace();
            return new ResponseEntity<Account>((Account)null, HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException e){
            e.printStackTrace();
            return new ResponseEntity<Account>((Account) null, HttpStatus.NOT_FOUND);
        }
    }
    /*
    @PostMapping("/postAccount")
    public void postAccount(@RequestParam ("id") @Min(1) @NotNull Long id,
                                               @RequestParam ("name") @NotNull String name) {
        //String url = getUrlProxy() + "/getAccount?id=" + id + "&name= " + name;
        String url = getUrlProxy() + "/getAccount?id=" + id;
        new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);

    }
    */
    //?
    @GetMapping("/postAccount")
    public ResponseEntity postAccount(@RequestParam("account") @Validated Account account) {
        String url = getUrlProxy() + "/postAccount?account=" + account;
        new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Account.class);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //переопределиние реагирования на ошибку BAD_REQUEST
    @ExceptionHandler(ConstraintViolationException.class) //@ExceptionHandler({1.class, 2.class, 3.class}) //для нескольких исключений
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
