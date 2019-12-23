package com.netckrecker;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
             "applicationContext.xml"
        ); //нужно для конфигурационного файла спринга

        TestBean testBean = context.getBean("testBean", TestBean.class);

        testBean.Data();

        context.close();


    }
}
