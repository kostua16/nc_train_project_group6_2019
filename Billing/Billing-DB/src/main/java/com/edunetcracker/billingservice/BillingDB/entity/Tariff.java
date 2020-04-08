package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_tariff")
public class Tariff {

    @Id
    private String name;
    /**
     * Call
     **/
    private Float call_cost;
    private Long call_balance;
    private Float default_call_cost;
    /**
     * Internet
     **/
    private Float internet_cost;
    private Long internet_balance;
    private Float default_internet_cost;
    /**
     * Sms
     **/
    private Float sms_cost;
    private Long sms_balance;
    private Float default_sms_cost;

    public Tariff(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ///////////////
    public Float getCall_cost() {
        return call_cost;
    }

    public void setCall_cost(Float call_cost) {
        this.call_cost = call_cost;
    }

    public Long getCall_balance() {
        return call_balance;
    }

    public void setCall_balance(Long call_balance) {
        this.call_balance = call_balance;
    }

    public Float getDefault_call_cost() {
        return default_call_cost;
    }

    public void setDefault_call_cost(Float default_call_cost) {
        this.default_call_cost = default_call_cost;
    }

    public Float getInternet_cost() {
        return internet_cost;
    }

    public void setInternet_cost(Float internet_cost) {
        this.internet_cost = internet_cost;
    }

    public Long getInternet_balance() {
        return internet_balance;
    }

    public void setInternet_balance(Long internet_balance) {
        this.internet_balance = internet_balance;
    }

    public Float getDefault_internet_cost() {
        return default_internet_cost;
    }

    public void setDefault_internet_cost(Float default_internet_cost) {
        this.default_internet_cost = default_internet_cost;
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

    public void setDefault_sms_cost(Float default_sms_cost) {
        this.default_sms_cost = default_sms_cost;
    }
}
