package com.netcracker.edu.group6;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomizerControl {
	
	@RequestMapping("/test2/test2")
	@ResponseBody
	String rand() {
		
		Random rnd = new Random(System.currentTimeMillis());
		int number = rnd.nextInt(1000);
		return "Your number is " + number;
	}
}