package com.edunetcracker.billingservice.BillingDB.ui.test;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import com.edunetcracker.billingservice.BillingDB.entity.Call;
import com.edunetcracker.billingservice.BillingDB.entity.Internet;
import com.edunetcracker.billingservice.BillingDB.entity.Sms;

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
