package com.stackroute.accountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AccountmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountmanagerApplication.class, args);
	}

}
