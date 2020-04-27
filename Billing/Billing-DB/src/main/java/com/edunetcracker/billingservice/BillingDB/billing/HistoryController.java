package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.History;
import com.edunetcracker.billingservice.BillingDB.services.IHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoryController {

    @Autowired
    IHistoryRepository historyRepository;

    /***************************************************************************************/

    @GetMapping("getAllHistory")
    public ResponseEntity<List<History>> getAllHistory() {
        List<History> histories = historyRepository.findAll();
        return new ResponseEntity<>(histories, HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("getHistory")
    public ResponseEntity<List<History>> getHistory() {
        List<History> histories = new ArrayList<>(historyRepository.findAll(PageRequest.of(0, 100, Sort.Direction.DESC, "Id")).toSet());
        return new ResponseEntity<>(histories, HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
