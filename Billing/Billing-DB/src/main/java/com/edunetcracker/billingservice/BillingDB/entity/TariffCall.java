package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_tariff_call")
public class TariffCall {

    @Id
    private String name;

    private Float call_cost;

    private Long call_balance;

    private Float default_call_cost;

    ////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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
}
