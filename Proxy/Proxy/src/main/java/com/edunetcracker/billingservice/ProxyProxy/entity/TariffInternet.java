package com.edunetcracker.billingservice.ProxyProxy.entity;

public class TariffInternet {

    private String name;

    private Float internet_cost;

    private Long internet_balance;

    private Float default_internet_cost;

////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
