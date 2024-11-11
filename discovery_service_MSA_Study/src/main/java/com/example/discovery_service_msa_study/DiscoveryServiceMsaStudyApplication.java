package com.example.discovery_service_msa_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 유래카 서버의 역할
public class DiscoveryServiceMsaStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceMsaStudyApplication.class, args);
	}

}
