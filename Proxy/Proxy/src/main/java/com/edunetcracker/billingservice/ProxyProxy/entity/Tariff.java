package com.edunetcracker.billingservice.ProxyProxy.entity;


public class Tariff {
    private String login;

    private String name;


    public Tariff(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
