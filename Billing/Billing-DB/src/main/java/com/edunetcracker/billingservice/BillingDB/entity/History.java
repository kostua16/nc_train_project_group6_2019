package com.edunetcracker.billingservice.BillingDB.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "test_history")
@SequenceGenerator(sequenceName = "history_sequence", name = "history_generator", initialValue = 1, allocationSize = 1)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_generator")
    private Long Id;

    private String date;

    private String type;

    private String body;

    public History() {

    }
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

