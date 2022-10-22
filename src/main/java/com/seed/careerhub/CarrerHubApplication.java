package com.seed.careerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class CarrerHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrerHubApplication.class, args);
	}

}
