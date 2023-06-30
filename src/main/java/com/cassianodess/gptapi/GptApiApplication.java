package com.cassianodess.gptapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@EnableConfigurationProperties
@EntityScan(
	basePackages = {"com.cassianodess.gptapi.models"}
)
@SpringBootApplication
public class GptApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptApiApplication.class, args);
	}

}
