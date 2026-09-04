package com.mihirjha.cloudai.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mihirjha.cloudai.queue.configuration.AwsProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class AiQueueAdvisorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiQueueAdvisorySystemApplication.class, args);
	}

}
