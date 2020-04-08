package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Tariff;
import com.edunetcracker.billingservice.BillingDB.services.ITariffRepository;
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
    ITariffRepository TariffRepository = null;

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
}
