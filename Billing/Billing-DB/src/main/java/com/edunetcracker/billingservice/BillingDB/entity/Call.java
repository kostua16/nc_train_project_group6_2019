package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.*;

@Entity
@Table(name = "test_call")
public class Call {

    @Id
    private String login;

    private Float call_cost;            //стоимость доступного ресурса

    private Long call_balance;          //количество доступного реурса

    private Float default_call_cost;    //стоимость без наличия ресурса

    //////////////////////////////////

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return "Call{" +
                "login='" + login + '\'' +
                ", call_cost=" + call_cost +
                ", call_balance=" + call_balance +
                ", default_call_cost=" + default_call_cost +
                '}';
    }

    public void setDefault_call_cost(Float default_call_cost) {
        this.default_call_cost = default_call_cost;
    }
}
