package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_internet")

public class Internet {

    @Id
    private String login;

    private Float internet_cost;

    private Long internet_balance;

    private Float default_internet_cost;

///////////////////////////////////////

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return "Internet{" +
                "login='" + login + '\'' +
                ", internet_cost=" + internet_cost +
                ", internet_balance=" + internet_balance +
                ", default_internet_cost=" + default_internet_cost +
                '}';
    }
}
