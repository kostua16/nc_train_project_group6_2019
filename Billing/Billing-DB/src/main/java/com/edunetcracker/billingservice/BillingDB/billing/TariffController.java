package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Tariff;
import com.edunetcracker.billingservice.BillingDB.entity.TariffCall;
import com.edunetcracker.billingservice.BillingDB.entity.TariffInternet;
import com.edunetcracker.billingservice.BillingDB.entity.TariffSms;
import com.edunetcracker.billingservice.BillingDB.services.ITariffCallRepository;
import com.edunetcracker.billingservice.BillingDB.services.ITariffInternetRepository;
import com.edunetcracker.billingservice.BillingDB.services.ITariffRepository;
import com.edunetcracker.billingservice.BillingDB.services.ITariffSmsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TariffController {

    @Autowired
    ITariffRepository TariffRepository ;

    @Autowired
    ITariffCallRepository TariffCallRepository ;

    @Autowired
    ITariffInternetRepository TariffInternetRepository ;

    @Autowired
    ITariffSmsRepository TariffSmsRepository ;

    @Autowired
    ObjectMapper objectMapper;

    /***************************************************************************************/

    @GetMapping("getTariffByName")
    public ResponseEntity<Tariff> getTariffByName(@RequestParam("name") String name) {

        Tariff tariff = TariffRepository.findTariffByName(name);
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    @GetMapping("getAllTariff")
    public ResponseEntity<List<Tariff>> getAllTariff() {

        List<Tariff> tariff = TariffRepository.findAll();
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }
    //
    @GetMapping("getTariffCallByName")
    public ResponseEntity<TariffCall> getTariffCallByName(@RequestParam("name") String name) {

        TariffCall tariff = TariffCallRepository.findTariffCallByName(name);
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    @GetMapping("getAllTariffCall")
    public ResponseEntity<List<TariffCall>> getAllTariffCall() {

        List<TariffCall> tariff = TariffCallRepository.findAll();
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }
    //
    @GetMapping("getTariffInternetByName")
    public ResponseEntity<TariffInternet> getTariffInternetByName(@RequestParam("name") String name) {

        TariffInternet tariff = TariffInternetRepository.findTariffInternetByName(name);
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    @GetMapping("getAllTariffInternet")
    public ResponseEntity<List<TariffInternet>> getAllTariffInternet() {

        List<TariffInternet> tariff = TariffInternetRepository.findAll();
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }
    //
    @GetMapping("getTariffSmsByName")
    public ResponseEntity<TariffSms> getTariffSmsByName(@RequestParam("name") String name) {

        TariffSms tariff = TariffSmsRepository.findTariffSmsByName(name);
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }

    @GetMapping("getAllTariffSms")
    public ResponseEntity<List<TariffSms>> getAllTariffSms() {

        List<TariffSms> tariff = TariffSmsRepository.findAll();
        return new ResponseEntity<>(tariff, HttpStatus.OK);
    }
}
