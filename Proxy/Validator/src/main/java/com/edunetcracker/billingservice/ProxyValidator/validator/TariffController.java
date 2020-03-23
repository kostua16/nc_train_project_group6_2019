package com.edunetcracker.billingservice.ProxyValidator.validator;

import com.edunetcracker.billingservice.ProxyValidator.entity.Tariff;
import com.edunetcracker.billingservice.ProxyValidator.helpers.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class TariffController {

    @Autowired
    private Helpers helpers;


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("getTariff")
    public ResponseEntity<Tariff> getTariff(@RequestParam("name") @NotNull String name) {

        String url = helpers.getUrlProxy() + "/getTariff/?name=" + name;
        try {
            ResponseEntity<Tariff> responsePoxy = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), Tariff.class);
            return new ResponseEntity<Tariff>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Tariff>((Tariff) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Tariff>((Tariff) null, HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("createTariff")
    public ResponseEntity<Boolean> createTariff(@Valid @RequestBody Tariff tariff) {

        String url = helpers.getUrlProxy() + "/createTariff";
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(tariff, new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateTariff")
    public ResponseEntity<Boolean> updateTariff(@RequestParam("name") @NotNull String name,
                                                @Valid @RequestBody Tariff newTariff) {
        String url = helpers.getUrlProxy() + "/updateTariff/?name=" + name;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.PUT, new HttpEntity<>(newTariff, new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("deleteTariff")
    public ResponseEntity<Boolean> deleteTariff(@RequestParam("name") @NotNull String name) {

        String url = helpers.getUrlProxy() + "/deleteTariff/name=" + name;
        try {
            ResponseEntity<Boolean> responsePoxy = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity(new HttpHeaders()), Boolean.class);
            return new ResponseEntity<Boolean>(responsePoxy.getBody(), responsePoxy.getStatusCode());

        } catch (HttpClientErrorException.NotFound e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return new ResponseEntity<Boolean>((Boolean) null, HttpStatus.NOT_FOUND);
        }
    }


}
