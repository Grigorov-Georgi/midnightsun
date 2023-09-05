package com.midnightsun.revrateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RevRateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevRateServiceApplication.class, args);
	}

}
