package com.edunetcracker.billingservice.ProxyProxy.entity;


public class Account {


    private String login;

    private String password;

    private String name;

    private Long balance;

    // private String rang;         // user, administrator...

    ///////////////////////////////////////
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
