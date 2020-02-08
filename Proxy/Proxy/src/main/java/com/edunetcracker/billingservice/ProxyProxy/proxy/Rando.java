package com.edunetcracker.billingservice.ProxyProxy.proxy;


public class Rando {
    Long getRandInt(long left, long right){
        return (left + (long) (Math.random() * (right - left)+1));
    }
}
