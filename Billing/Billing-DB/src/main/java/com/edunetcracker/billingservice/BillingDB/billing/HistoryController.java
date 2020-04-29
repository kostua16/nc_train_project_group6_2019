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
import java.util.Comparator;
import java.util.List;

@RestController
public class HistoryController implements Comparator<History> {

    @Autowired
    IHistoryRepository historyRepository;

    /***************************************************************************************/


    @GetMapping("getHistory")
    public ResponseEntity<List<History>> getHistory(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<History> histories = new ArrayList<>(historyRepository.findAll(PageRequest.of(page, 100, Sort.Direction.DESC, "id")).toSet());
        histories.sort(this);
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    @Override
    public int compare(History o1, History o2) {
        if(o1!=null){
            if(o2!=null){
                if(o1.getId().equals(o2.getId())){
                    return 0;
                } else {
                    if(o1.getId()> o2.getId()){
                        return 1;
                    } else {
                        return -1;
                    }
                }
            } else {
                return  1;
            }
        } else {
            if(o2!=null){
                return -1;
            } else {
                return 0;
            }
        }
    }

}
