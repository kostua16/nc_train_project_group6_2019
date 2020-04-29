package com.edunetcracker.billingservice.BillingDB.billing;

import com.edunetcracker.billingservice.BillingDB.entity.History;
import com.edunetcracker.billingservice.BillingDB.services.IHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<History>> getHistory(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<History> histories = new ArrayList<>(historyRepository.findAll(PageRequest.of(page, 50, Sort.Direction.DESC, "id")).toSet());
        return new ResponseEntity<>(histories, HttpStatus.OK);
        //return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
