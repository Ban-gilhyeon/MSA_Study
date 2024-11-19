package com.example.first_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service/")
@Slf4j
@RequiredArgsConstructor
public class FirstServiceController {

	private final Environment env;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to First Service";
	}

	@GetMapping("/message")
	public String message(@RequestHeader("first_request") String header) {
		log.info(header);
		return "Hello First Service";
	}

	@GetMapping("/check")
	public String check(HttpServletRequest request) {
		log.info("Sever Port ={}",request.getLocalPort());
		return String.format("Chcek HI First_Service from First Service on PORT %s",
				env.getProperty("local.server.port"));
	}
}

