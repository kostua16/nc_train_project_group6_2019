package com.edunetcracker.billingservice.ProxyProxy.web;

import com.edunetcracker.billingservice.ProxyProxy.proxy.Imitator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("imitator")
public class ImitatorRest {

    @Autowired
    Imitator imitator;

    @GetMapping("")
    public String imitator(Model model){
        model.addAttribute("enabled", imitator.isStarted());
        return "ui/test/imitator";
    }
    @GetMapping("start")
    public Boolean imitatorStart(){
        imitator.start();
        return true;
    }
    @GetMapping("stop")
    public Boolean imitatorStop(){
        imitator.stop();
        return true;
    }
    @GetMapping("oneGeneration")
    public Boolean imitatorRunGenerate() throws JsonProcessingException {
        imitator.manuallyGenerateUsers();
        return true;
    }
    @GetMapping("oneUsage")
    public Boolean imitatorRunUsage() {
        imitator.manuallyMadeCalls();
        return true;
    }
}
