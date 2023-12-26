package com.demo.proclientmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan("com.demo.proclientmanager")
@EntityScan("com.demo.proclientmanager.model")
public class ProClientManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProClientManagerApplication.class, args);
	}

}
