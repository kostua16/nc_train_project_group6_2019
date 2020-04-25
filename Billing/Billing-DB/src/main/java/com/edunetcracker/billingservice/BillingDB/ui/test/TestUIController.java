package com.edunetcracker.billingservice.BillingDB.ui.test;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.entity.Tariff;
import com.edunetcracker.billingservice.BillingDB.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller()
@RequestMapping("/ui/test")
public class TestUIController {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ITariffRepository tariffRepository;

    @Autowired
    private ITariffCallRepository tariffCallRepository;

    @Autowired
    private ITariffSmsRepository tariffSmsRepository;

    @Autowired
    private ITariffInternetRepository tariffInternetRepository;

    @Autowired
    private ICallRepository callRepository;

    @Autowired
    private ISmsRepository smsRepository;

    @Autowired
    private IInternetRepository internetRepository;

    @GetMapping("users")
    public String users(Model model){
        List<Account> allAccounts = accountRepository.findAll();
        List<BalanceInfo> balances = new ArrayList<>();
        for (Account account : allAccounts) {
            BalanceInfo balanceInfo = new BalanceInfo();
            balanceInfo.setAccount(account);
            balanceInfo.setCallBalance(callRepository.findCallByLogin(account.getLogin()));
            balanceInfo.setSmsBalance(smsRepository.findSmsByLogin(account.getLogin()));
            balanceInfo.setInternetBalance(internetRepository.findInternetByLogin(account.getLogin()));
            balances.add(balanceInfo);
        }
        model.addAttribute("balances", balances);
        return "ui/test/users";
    }

    @GetMapping("plans")
    public String tariffs(Model model){
        List<Tariff> allTariffs = tariffRepository.findAll();
        List<PricePlanInfo> plans = new ArrayList<>();
        for (Tariff tariff : allTariffs) {
            PricePlanInfo planInfo = new PricePlanInfo();
            planInfo.setTariff(tariff);
            planInfo.setTariffCall(tariffCallRepository.findTariffCallByName(tariff.getName()));
            planInfo.setTariffSms(tariffSmsRepository.findTariffSmsByName(tariff.getName()));
            planInfo.setTariffInternet(tariffInternetRepository.findTariffInternetByName(tariff.getName()));
            plans.add(planInfo);
        }
        model.addAttribute("plans", plans);
        return "ui/test/plans";
    }
}
