package com.stackroute.jobhuntapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JobHuntApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobHuntApiGatewayApplication.class, args);
	}

}
