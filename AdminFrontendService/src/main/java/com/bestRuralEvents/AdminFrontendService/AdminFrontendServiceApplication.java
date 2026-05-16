package com.bestRuralEvents.AdminFrontendService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AdminFrontendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminFrontendServiceApplication.class, args);
	}

}
