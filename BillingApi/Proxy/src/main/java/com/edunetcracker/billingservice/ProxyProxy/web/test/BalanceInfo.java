package com.edunetcracker.billingservice.ProxyProxy.web.test;

import com.edunetcracker.billingservice.ProxyProxy.entity.Account;
import com.edunetcracker.billingservice.ProxyProxy.entity.Call;
import com.edunetcracker.billingservice.ProxyProxy.entity.Internet;
import com.edunetcracker.billingservice.ProxyProxy.entity.Sms;

public class BalanceInfo {
    private Account account;
    private Call callBalance;
    private Sms smsBalance;
    private Internet internetBalance;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Call getCallBalance() {
        return callBalance;
    }

    public void setCallBalance(Call callBalance) {
        this.callBalance = callBalance;
    }

    public Sms getSmsBalance() {
        return smsBalance;
    }

    public void setSmsBalance(Sms smsBalance) {
        this.smsBalance = smsBalance;
    }

    public Internet getInternetBalance() {
        return internetBalance;
    }

    public void setInternetBalance(Internet internetBalance) {
        this.internetBalance = internetBalance;
    }
}
