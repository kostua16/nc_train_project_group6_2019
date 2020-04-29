package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/*@RestController*/
@Service
public class HistoryController implements Comparator<History> {

    @Autowired
    OperationsService operationsService;

    /////////////////////////////////////////////////////////////////////////////////////////////

    public List<History> showHistory(Integer page) {
        final List<History> histories = operationsService.requestList("/getHistory?page=" + page, HttpMethod.GET, History[].class);
        histories.sort(this);
        return histories;
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
