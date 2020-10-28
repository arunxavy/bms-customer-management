package com.bms.customer.mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class CustomerManagementApplication {

	public static void main(String[] args) {// NOSONAR
		SpringApplication.run(CustomerManagementApplication.class, args);// NOSONAR
	}

}
