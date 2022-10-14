package com.kl.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.kl.webflux.repository.BookRepository;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = BookRepository.class)
public class WebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

}
