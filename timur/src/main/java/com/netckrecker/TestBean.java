package com.netckrecker;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TestBean {
    public void  Data (){
        LocalDate date = LocalDate.now(); // получаем текущую дату
        int year = date.getYear(); //год
        int month = date.getMonthValue(); //месяц
        int dayOfMonth = date.getDayOfMonth(); //день
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println(date);
        System.out.println(dayOfWeek);
        System.out.printf("%d.%d.%d \n", dayOfMonth, month, year);
    }
}
