package com.example.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Greeting {
	@Value("${greeting.message}") // application.yml에 있는 변수를 사용할 때 사용
	private String message;
}
