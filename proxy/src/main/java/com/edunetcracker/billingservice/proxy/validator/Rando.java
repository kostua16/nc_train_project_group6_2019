package com.edunetcracker.billingservice.proxy.validator;


import java.util.Random;

public class Rando {
    static int getRandInt(int left, int right){
        return (left + (int) (Math.random() * (right-left)+1));
    }
}
