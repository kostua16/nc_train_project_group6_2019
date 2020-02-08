package com.edunetcracker.billingservice.ProxyValidator.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Account {

    @Size(min = 0)
    @NotNull
    private Long id = null;
    @NotNull
    private String name = null;
    @NotNull
    private Long balance = null;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
