package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.Call;
import com.edunetcracker.billingservice.BillingDB.services.ICallRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CallController {

    @Autowired
    ICallRepository CallRepository = null;

    @Autowired
    ObjectMapper objectMapper;

    /***************************************************************************************/

    // get Entity
    @GetMapping("getCallByLogin")
    public ResponseEntity<Call> getCallByLogin(@RequestParam("login") String login) {

        Call call = CallRepository.findCallByLogin(login);
        return new ResponseEntity<>(call, HttpStatus.OK);
    }

    @GetMapping("getAllCall")
    public ResponseEntity<List<Call>> getAllCall() {

        List<Call> calls = CallRepository.findAll();
        return new ResponseEntity<>(calls, HttpStatus.OK);
    }

    /****************************/
    // get Entity field
    @GetMapping("getCallCostByLogin")
    public ResponseEntity<Float> getCallCostByLogin(@RequestParam("login") String login) {
        Call call = CallRepository.findCallByLogin(login);
        return new ResponseEntity<>(call.getCall_cost(), HttpStatus.OK);
    }

    @GetMapping("getDefaultCallCost")
    public ResponseEntity<Float> getDefaultCallCost(@RequestParam("login") String login) {
        Call call = CallRepository.findCallByLogin(login);
        return new ResponseEntity<>(call.getDefault_call_cost(), HttpStatus.OK);
    }

    @GetMapping("getCallBalance")
    public ResponseEntity<Long> getCallBalance(@RequestParam("login") String login) {
        Call call = CallRepository.findCallByLogin(login);
        return new ResponseEntity<>(call.getCall_balance(), HttpStatus.OK);
    }


}
