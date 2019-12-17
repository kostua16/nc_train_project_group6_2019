package com.netcracker.edu.group6;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

public class ReadTextFile {
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
