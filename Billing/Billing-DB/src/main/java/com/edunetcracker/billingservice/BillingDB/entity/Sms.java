package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_sms")
public class Sms {

    @Id
    private String login;

    private Float sms_cost;

    private Long sms_balance;

    private Float default_sms_cost;

    ////////////////////////////////////////

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public Float getSms_cost() {
        return sms_cost;
    }

    public void setSms_cost(Float sms_cost) {
        this.sms_cost = sms_cost;
    }

    public Long getSms_balance() {
        return sms_balance;
    }

    public void setSms_balance(Long sms_balance) {
        this.sms_balance = sms_balance;
    }

    public Float getDefault_sms_cost() {
        return default_sms_cost;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "login='" + login + '\'' +
                ", sms_cost=" + sms_cost +
                ", sms_balance=" + sms_balance +
                ", default_sms_cost=" + default_sms_cost +
                '}';
    }

    public void setDefault_sms_cost(Float default_sms_cost) {
        this.default_sms_cost = default_sms_cost;
    }

}
