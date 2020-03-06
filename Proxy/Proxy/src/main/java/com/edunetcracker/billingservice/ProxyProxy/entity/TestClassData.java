package com.edunetcracker.billingservice.ProxyProxy.entity;

public class TestClassData {
    public String a = "test";
    public TestClassData(String s){
        a = s;
    }

    @Override
    public String toString() {
        return  a;
    }
}
