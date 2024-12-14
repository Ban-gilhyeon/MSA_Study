package com.example.second_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service/")
@Slf4j
@RequiredArgsConstructor
public class SecondServiceController {
	private final Environment env;
	private final FirstServiceClient firstServiceClient;
	@GetMapping("/welcome")
	public String welcome() {
		log.debug("Welcome to SecondServiceController");
		return "Welcome to SecondService!" + firstServiceClient.welcome();
	}

	@GetMapping("/message")
	public String message(@RequestHeader("second-request") String header) {
		log.info(header);
		return "Hello Second Service";
	}

	@GetMapping("/check")
	public String check(HttpServletRequest request) {
		log.info("Server Port ={}",request.getServerPort());
		return String.format("Chcek HI Second_Service from second service on PORT %s"
				,env.getProperty("local.server.port"));
	}
}
