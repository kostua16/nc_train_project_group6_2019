package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.*;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/*@RestController*/
@Service
public class TariffController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;

    @Autowired
    ObjectMapper objectMapper;

    /////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping("getTariff")*/
    public ResponseEntity<Tariff> getTariff(/*@RequestParam("token") String token ,
                                            @RequestParam("name") */String name ) {
        try {
            String url = helpers.getUrlBilling() + "/getTariffByName/?name=" + name;
            ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Tariff.class);
            return new ResponseEntity<>((Tariff) responseAccount.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Tariff) null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Tariff>> getAllTariff() {
        try {
            String url = helpers.getUrlBilling() + "/getAllTariff";
            ResponseEntity <List<Tariff>> response = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), new ParameterizedTypeReference<List<Tariff>>() {});
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<CollectedTariff> getCollectedTariffByName(String tariffName) {
        try {
            String url;
            url = helpers.getUrlBilling() + "/getTariffCallByName/?name="+tariffName;
            TariffCall call = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffCall.class).getBody();
            url = helpers.getUrlBilling() + "/getTariffInternetByName/?name="+tariffName;
            TariffInternet internet = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffInternet.class).getBody();
            url = helpers.getUrlBilling() + "/getTariffSmsByName/?name="+tariffName;
            TariffSms sms = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffSms.class).getBody();
            CollectedTariff tariff = new CollectedTariff(tariffName, call, internet, sms);
            return new ResponseEntity<>( tariff, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<CollectedTariff>> getAllCollectedTariff() {
        try {
            List<Tariff> tariffs = getAllTariff().getBody();
            List<CollectedTariff> collectedTariffs = new ArrayList<>();
            String name;
            String url;
            for(int a = 0; a < tariffs.size(); a++){
                name = tariffs.get(a).getName();
                url = helpers.getUrlBilling() + "/getTariffCallByName/?name="+name;
                TariffCall call = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffCall.class).getBody();
                url = helpers.getUrlBilling() + "/getTariffInternetByName/?name="+name;
                TariffInternet internet = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffInternet.class).getBody();
                url = helpers.getUrlBilling() + "/getTariffSmsByName/?name="+name;
                TariffSms sms = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), TariffSms.class).getBody();
                CollectedTariff tariff = new CollectedTariff(name, call, internet, sms);
                collectedTariffs.add(tariff);
            }
            return new ResponseEntity<>( collectedTariffs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    // {
    //"www": {"v":"ewd"},
    //"ggg": {"a":"ddd"}
    //}

    /*@PostMapping("Test")
    public String aaa(@RequestBody Map<String, Map<String,String>> map) throws JsonProcessingException {
        ///Map<String, String> map
        //System.out.println(map.get("ggg"));
        System.out.println(map.toString());
        System.out.println(map.get("ggg"));
        System.out.println(map.get("ggg").get("a"));
        //ggg k = objectMapper.readValue(map.get("ggg"), ggg.class);
        //return k.a;
        return "Good";
    }
    @GetMapping("GetList")
    public Map<String, String> hhb() throws JsonProcessingException {
        Account account = new Account();
        Call call = new Call();
        ggg ggg = new ggg();
        Map<String, String> h = new HashMap<>();
        h.put("acount", objectMapper.writeValueAsString(account));
        h.put("call", objectMapper.writeValueAsString(call));
        h.put("ggg", objectMapper.writeValueAsString(ggg));
        return h;
    }*/

    /*@PostMapping("createTariff")*/
    public ResponseEntity<Boolean> createTariff(
                                                /*@RequestBody Tariff tariff*/
                                                /*@RequestBody Map tariffs*/) {
        try {

                ///
                try{
                    //Gson
                }
                catch (Exception e){

                }

                ///
                //if (checks.isTariffExists(tariff.getName())) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    /*@PutMapping("updateTariff")*/
    public ResponseEntity<Boolean> updateTariff(/*@RequestParam("token") String token,
                                                 @RequestBody */Tariff newTariff) {
        try {
            rabbitMQSender.send(newTariff, RabbitMQMessageType.UPDATE_TARIFF);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }

    /*@DeleteMapping("deleteTariff")*/
    public ResponseEntity<Boolean> deleteTariff(/*@RequestParam("token") String token,
                                                @RequestParam("name")*/ String name) {
        try {
            rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Boolean> deleteCollectedTariffByName(String name) {
        try {
            rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF);
            rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_CALL);
            rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_INTERNET);
            rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF_SMS);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
    //update all accounts by their tariff plans
    /*@GetMapping("newMonth")*/
    public Boolean newMonth() {
        try {

            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
