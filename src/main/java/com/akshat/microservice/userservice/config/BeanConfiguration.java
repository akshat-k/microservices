package com.akshat.microservice.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

	@Bean
	@LoadBalanced // Used to load the balanace of server as well as calling services by name 
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
