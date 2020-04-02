package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import com.edunetcracker.billingservice.ProxyProxy.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TariffController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getTariff")
    public ResponseEntity<Tariff> getTariff(@RequestParam("token") String token ,
                                            @RequestParam("name") String name ) {
        try {
            if (sessionService.inSession(token)) {

                if (checks.isTariffExists(name)) {
                    //TODO GET
                    String url = helpers.getUrlBilling() + "/getTariff/?name=" + name;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Tariff.class);

                    return new ResponseEntity<>((Tariff) responseAccount.getBody(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>((Tariff) null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Tariff) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("createTariff")
    public ResponseEntity<Boolean> createTariff(@RequestParam("token") String token ,
                                                @RequestBody Tariff tariff) {
        try {
            if (sessionService.inSession(token)) {
                if (checks.isTariffExists(tariff.getName())) {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

                } else {
                    //TODO RABBIT
                    rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
                    return new ResponseEntity<>(true, HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateTariff")
    public ResponseEntity<Boolean> updateTariff(@RequestParam("token") String token,
                                                 @RequestBody Tariff newTariff) {
        try {
            if (sessionService.inSession(token)) {
                if (checks.isTariffExists(newTariff.getName())) {
                    //TODO RABBIT
                    rabbitMQSender.send(newTariff, RabbitMQMessageType.UPDATE_TARIFF);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteTariff")
    public ResponseEntity<Boolean> deleteTariff(@RequestParam("token") String token,
                                                @RequestParam("name") String name) {

        try {
            if (sessionService.inSession(token)) {
                if (checks.isTariffExists(name)) {
                    //TODO RABBIT
                    rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
