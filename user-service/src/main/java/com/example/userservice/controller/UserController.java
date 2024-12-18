package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.service.UserServiceImpl;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-service")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final Environment env;
	private final Greeting greeting;
	private final UserService userService;

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

	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody RequestUser user){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUSer(userDto);

		RequestUser requestUser = mapper.map(userDto, RequestUser.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(requestUser);
	}
}
