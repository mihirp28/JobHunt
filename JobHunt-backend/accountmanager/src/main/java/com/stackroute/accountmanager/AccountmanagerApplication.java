package com.stackroute.accountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.accountmanager.filter.JwtFilter;

@SpringBootApplication
@EnableEurekaClient
public class AccountmanagerApplication {
	
	@Bean
	public FilterRegistrationBean jwtFilter(){
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(AccountmanagerApplication.class, args);
	}

}
