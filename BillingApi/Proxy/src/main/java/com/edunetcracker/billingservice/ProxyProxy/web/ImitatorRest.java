package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.proxy.ImitatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/imitator")
public class ImitatorRest {

    @Autowired
    ImitatorService imitatorService;

    @GetMapping("status")
    public Boolean imitator(){
        return imitatorService.isStarted();
    }
    @GetMapping("start")
    public Boolean imitatorStart(){
        imitatorService.start();
        return true;
    }
    @GetMapping("stop")
    public Boolean imitatorStop(){
        imitatorService.stop();
        return true;
    }
    @GetMapping("oneGeneration")
    public Boolean imitatorRunGenerate() throws JsonProcessingException {
        imitatorService.manuallyGenerateUsers();
        return true;
    }
    @GetMapping("oneUsage")
    public Boolean imitatorRunUsage() {
        imitatorService.manuallyMadeCalls();
        return true;
    }
}
