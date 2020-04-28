package com.edunetcracker.billingservice.ProxyProxy.proxy;

import com.edunetcracker.billingservice.ProxyProxy.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

/*@RestController*/
@Service
public class HistoryController {

    @Autowired
    OperationsService operationsService;

    /////////////////////////////////////////////////////////////////////////////////////////////

    public List<History> showHistory(Integer page) {
        return operationsService.requestList("/getHistory?page=" + page, HttpMethod.GET, History[].class);
    }

}
