package com.edunetcracker.billingservice.BillingDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class BillingDbApplication {

	private static EntityManagerFactory factory;
	public static void main(String[] args) {
		SpringApplication.run(BillingDbApplication.class, args);
	}

}
