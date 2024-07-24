package com.fils.fils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.fils.fils.repository")
@EntityScan(basePackages = "com.fils.fils.model")
public class FilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilsApplication.class, args);
	}

}
