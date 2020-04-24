package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Internet;
import com.edunetcracker.billingservice.BillingDB.services.IInternetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InternetController {

    @Autowired
    IInternetRepository InternetRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    /***************************************************************************************/

    // get Entity
    @GetMapping("getInternetByLogin")
    public ResponseEntity<Internet> getInternetByLogin(@RequestParam("login") String login) {

        Internet internet = InternetRepository.findInternetByLogin(login);
        return new ResponseEntity<>(internet, HttpStatus.OK);
    }

    @GetMapping("getAllInternet")
    public ResponseEntity<List<Internet>> getAllInternet() {

        List<Internet> calls = InternetRepository.findAll();
        return new ResponseEntity<>(calls, HttpStatus.OK);
    }

    /****************************/
    // get Entity field
    @GetMapping("getInternetCostByLogin")
    public ResponseEntity<Float> getInternetCostByLogin(@RequestParam("login") String login) {
        Internet internet = InternetRepository.findInternetByLogin(login);
        return new ResponseEntity<>(internet.getInternet_cost(), HttpStatus.OK);
    }

    @GetMapping("getDefaultInternetCost")
    public ResponseEntity<Float> getDefaultInternetCost(@RequestParam("login") String login) {
        Internet internet = InternetRepository.findInternetByLogin(login);
        return new ResponseEntity<>(internet.getDefault_internet_cost(), HttpStatus.OK);
    }

    @GetMapping("getInternetBalance")
    public ResponseEntity<Long> getInternetBalance(@RequestParam("login") String login) {
        Internet internet = InternetRepository.findInternetByLogin(login);
        return new ResponseEntity<>(internet.getInternet_balance(), HttpStatus.OK);
    }
}
