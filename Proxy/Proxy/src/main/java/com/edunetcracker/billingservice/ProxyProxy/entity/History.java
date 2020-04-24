package com.edunetcracker.billingservice.ProxyProxy.entity;

import java.time.LocalDate;

public class History {

    private Long Id;

    private String date;

    private String type;

    private String body;

    public History(String date, String type, String body) {
        this.date = date;
        this.type = type;
        this.body = body;
    }

    /**************************************/

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

