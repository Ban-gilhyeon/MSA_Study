package com.example.second_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MY-FIRST-SERVICE")
public interface FirstServiceClient {
	@GetMapping("/first-service/welcome")
	String welcome();
}
