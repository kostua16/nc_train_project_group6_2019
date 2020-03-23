package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Checks;
import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQMessageType;
import com.edunetcracker.billingservice.ProxyProxy.rabbit.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TariffController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private Helpers helpers;

    @Autowired
    private Checks checks;


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getTariff")
    public ResponseEntity<Tariff> getTariff(@RequestParam("name") String name ) {
        try {
            //существует или нет
            Boolean accountExists = checks.isTariffExists(name);
            //да - получить, нет - ошибка
            if (accountExists != null) {
                if (accountExists) {
                    String url = helpers.getUrlBilling() + "/getTariff/?name=" + name;
                    ResponseEntity responseAccount = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Tariff.class);

                    return new ResponseEntity<>((Tariff) responseAccount.getBody(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>((Tariff) null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>((Tariff) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((Tariff) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("createTariff")
    public ResponseEntity<Boolean> createTariff(@RequestBody Tariff tariff) {
        try {
            //существует или нет
            Boolean tariffExists = checks.isAccountExists(tariff.getName());

            //да - ошибка, нет - создать
            if (tariffExists != null) {
                if (tariffExists) {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

                } else {
                    rabbitMQSender.send(tariff, RabbitMQMessageType.CREATE_TARIFF);
                    return new ResponseEntity<>(true, HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateTariff")
    public ResponseEntity<Boolean> updateTariff(@RequestParam("name") String name,
                                                 @RequestBody Tariff newTariff) {
        try {
            Boolean tariffExists = checks.isTariffExists(name);

            newTariff.setName(name);// на всякий случай

            // если существует, то обновить
            if (tariffExists) {
                rabbitMQSender.send(newTariff, RabbitMQMessageType.UPDATE_TARIFF);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteTariff")
    public ResponseEntity<Boolean> deleteTariff(@RequestParam("name") String name) {

        try {
            Boolean tariffExists = checks.isTariffExists(name);

            // если существует, то удалить
            if (tariffExists) {
                rabbitMQSender.send(name, RabbitMQMessageType.DELETE_TARIFF);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
