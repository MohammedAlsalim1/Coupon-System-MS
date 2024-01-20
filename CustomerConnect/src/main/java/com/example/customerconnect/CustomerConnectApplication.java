package com.example.customerconnect;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Coupon System",
				version = "1.0.0",
				description = "This project for managing the coupons",
				contact = @Contact(
						name = "Mr Mohammed",
						email = "info.mohammed.com"
				), license = @License(
				name = "licence",
				url = "http://wwww.mohammed.com"
		)
		)
)
public class CustomerConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerConnectApplication.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
