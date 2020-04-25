package com.edunetcracker.billingservice.BillingDB.ui;

import com.edunetcracker.billingservice.BillingDB.services.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/test")
public class TestController {

    @Autowired
    private IAccountRepository accountRepository;

    @GetMapping("users")
    public String users(Model model){
        model.addAttribute("accounts", accountRepository.findAll());
        return "ui_test_users";
    }
}
