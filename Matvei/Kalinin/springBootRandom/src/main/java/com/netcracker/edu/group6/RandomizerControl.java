package com.netcracker.edu.group6;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netcracker.edu.group6.Randomizer;

@RestController
public class RandomizerControl {

	int result = Randomizer.getNumber();
	
	@RequestMapping("/test2/test2")
	@ResponseBody
	String rand() {
		return "Your number is " + result;
	}
}