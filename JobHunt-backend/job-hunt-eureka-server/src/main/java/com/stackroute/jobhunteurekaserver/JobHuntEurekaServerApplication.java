package com.stackroute.jobhunteurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JobHuntEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobHuntEurekaServerApplication.class, args);
	}

}
