package com.edunetcracker.billingservice.ProxyProxy.web.test;

import com.edunetcracker.billingservice.ProxyProxy.checks_and_helpers.Helpers;
import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.CollectedTariff;
import com.edunetcracker.billingservice.ProxyProxy.entity.History;
import com.edunetcracker.billingservice.ProxyProxy.entity.Tariff;
import com.edunetcracker.billingservice.ProxyProxy.proxy.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/ui/test")
public class TestUIController {

    @Autowired
    AccountController accountController;

    @Autowired
    CallController callController;

    @Autowired
    SmsController smsController;

    @Autowired
    InternetController internetController;

    @Autowired
    TariffController tariffController;

    @Autowired
    Helpers helpers;

    @Autowired
    ImitatorService imitatorService;


    @GetMapping("users")
    public String users(Model model){
        ;
        List<Account> allAccounts = accountController.getAllAccount().getBody();
        List<BalanceInfo> balances = new ArrayList<>();
        if(allAccounts!=null){
            for (Account account : allAccounts) {
                BalanceInfo balanceInfo = new BalanceInfo();
                balanceInfo.setAccount(account);
                balanceInfo.setCallBalance(callController.getCallByLogin(account.getLogin()).getBody());
                balanceInfo.setSmsBalance(smsController.getSmsByLogin(account.getLogin()).getBody());
                balanceInfo.setInternetBalance(internetController.getInternetByLogin(account.getLogin()).getBody());
                balances.add(balanceInfo);
            }
        }
        model.addAttribute("balances", balances);
        return "ui/test/users";
    }

    @GetMapping("plans")
    public String tariffs(Model model){
        List<Tariff> allTariffs = tariffController.getAllTariff().getBody();
        List<CollectedTariff> plans = new ArrayList<>();
        for (Tariff tariff : allTariffs) {
            final CollectedTariff collectedTariff = tariffController.getCollectedTariffByName(tariff.getName()).getBody();
            plans.add(collectedTariff);
        }
        model.addAttribute("plans", plans);
        return "ui/test/plans";
    }

    @GetMapping("history")
    public String history(Model model){
        String url = helpers.getUrlBilling() + "/getAllHistory";
        final List<History> historyList =  (List<History>) new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), List.class).getBody();
        model.addAttribute("history", historyList);
        return "ui/test/history";
    }

    @GetMapping("imitator")
    public String imitator(Model model){
        model.addAttribute("enabled", imitatorService.isStarted());
        return "ui/test/imitator";
    }
    @GetMapping("imitator/start")
    public String imitatorStart(){
        imitatorService.start();
        return "redirect:/ui/test/imitator";
    }
    @GetMapping("imitator/stop")
    public String imitatorStop(){
        imitatorService.stop();
        return "redirect:/ui/test/imitator";
    }
    @GetMapping("imitator/oneGeneration")
    public String imitatorRunGenerate() throws JsonProcessingException {
        imitatorService.manuallyGenerateUsers();
        return "redirect:/ui/test/imitator";
    }
    @GetMapping("imitator/oneUsage")
    public String imitatorRunUsage() {
        imitatorService.manuallyMadeCalls();
        return "redirect:/ui/test/imitator";
    }
}
