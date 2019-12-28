package com.netcracker.edu.group6;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class ReadTextFileControl {
    @RequestMapping("/")
    public String home() {
        return "Change to localhost:8080/test1/test1";
    }

    @RequestMapping("/test1/test1")
    public String test1_test1() {
        return read();
    }

    static String read(){
        String out = " ";
        try {
            Resource resource = new ClassPathResource("test1.txt");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), "UTF8"));
            String nextString;
            while ((nextString = br.readLine()) != null) {
                out += nextString;
            }
        } catch (IOException e) {
            e.printStackTrace();
            out = "Что-то пошло не так...";
        }
        return out;
    }
}

