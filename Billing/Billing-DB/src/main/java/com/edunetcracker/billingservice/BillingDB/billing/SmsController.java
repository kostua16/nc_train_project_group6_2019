package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Sms;
import com.edunetcracker.billingservice.BillingDB.services.ISmsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SmsController {

    @Autowired
    ISmsRepository SmsRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    /***************************************************************************************/
    // get Entity
    @GetMapping("getSmsByLogin")
    public ResponseEntity<Sms> getSmsByLogin(@RequestParam("login") String login) {

        Sms sms = SmsRepository.findSmsByLogin(login);
        return new ResponseEntity<>(sms, HttpStatus.OK);
    }

    @GetMapping("getAllSms")
    public ResponseEntity<List<Sms>> getAllSms() {

        List<Sms> sms = SmsRepository.findAll();
        return new ResponseEntity<>(sms, HttpStatus.OK);
    }

    /****************************/
    // get Entity field
    @GetMapping("getSmsCostByLogin")
    public ResponseEntity<Float> getSmsCostByLogin(@RequestParam("login") String login) {
        Sms sms = SmsRepository.findSmsByLogin(login);
        return new ResponseEntity<>(sms.getSms_cost(), HttpStatus.OK);
    }

    @GetMapping("getDefaultSmsCost")
    public ResponseEntity<Float> getDefaultSmsCost(@RequestParam("login") String login) {
        Sms sms = SmsRepository.findSmsByLogin(login);
        return new ResponseEntity<>(sms.getDefault_sms_cost(), HttpStatus.OK);
    }

    @GetMapping("getSmsBalance")
    public ResponseEntity<Long> getSmsBalance(@RequestParam("login") String login) {
        Sms sms = SmsRepository.findSmsByLogin(login);
        return new ResponseEntity<>(sms.getSms_balance(), HttpStatus.OK);
    }
}
