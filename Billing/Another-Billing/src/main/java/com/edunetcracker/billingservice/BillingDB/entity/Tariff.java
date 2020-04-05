package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_tariff")
public class Tariff {
    @Id
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
