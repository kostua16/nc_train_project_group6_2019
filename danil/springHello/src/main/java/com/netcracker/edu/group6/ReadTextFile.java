package com.netcracker.edu.group6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadTextFile {
    static String read(String path){
        String out = "";
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), "UTF8"));
            String nextString;
            while ((nextString = br.readLine()) != null) {
                out += nextString;
            }
        }
        catch(IOException ex){
            out = "Что-то пошло не так...";
        }
        return out;
    }
}
