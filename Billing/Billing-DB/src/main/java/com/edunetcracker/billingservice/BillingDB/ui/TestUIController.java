package com.edunetcracker.billingservice.BillingDB.ui;

import com.edunetcracker.billingservice.BillingDB.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/ui/test")
public class TestUIController {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ITariffRepository tariffRepository;
    private ITariffCallRepository tariffCallRepository;
    private ITariffSmsRepository tariffSmsRepository;
    private ITariffInternetRepository tariffInternetRepository;

    @GetMapping("users")
    public String users(Model model){
        model.addAttribute("accounts", accountRepository.findAll());
        return "ui_test_users";
    }

    @GetMapping("price_plans")
    public String tariffs(Model model){
        model.addAttribute("plans", tariffRepository.findAll());
        model.addAttribute("calls", tariffCallRepository.findAll());
        model.addAttribute("sms", tariffSmsRepository.findAll());
        model.addAttribute("internet", tariffInternetRepository.findAll());
        return "ui_test_price_plans";
    }
}
