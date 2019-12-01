package com.netcracker.edu.group6;
import java.util.Random;

public class Randomizer {

	private static int number;

	public Randomizer(int number) {
		this.number = number;
	}

	public static int getNumber() {
		Random rnd = new Random(System.currentTimeMillis());
		number = rnd.nextInt(1000);
		return number;
	}
}