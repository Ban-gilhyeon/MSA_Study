package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final Environment env;
	private final Greeting greeting;

	@GetMapping("/health_check")
	public String status(){
		return "It's working in User Service";
	}

	@GetMapping("/welcome")
	public String welcome(){
//		log.info("welcome api");
//		log.info(env.getProperty("greeting.message"));
//		return env.getProperty("greeting.message");
		return greeting.getMessage();
	}
}
