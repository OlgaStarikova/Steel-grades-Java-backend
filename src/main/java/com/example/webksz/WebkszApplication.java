package com.example.webksz;

import com.example.webksz.model.User;
import com.example.webksz.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebkszApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebkszApplication.class, args);
	}

}
