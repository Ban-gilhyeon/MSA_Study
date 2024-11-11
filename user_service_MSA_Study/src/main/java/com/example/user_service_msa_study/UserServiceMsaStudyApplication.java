package com.example.user_service_msa_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceMsaStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceMsaStudyApplication.class, args);
	}

}
