package com.banking.springbootbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBankingApplication.class, args);
	}

}
