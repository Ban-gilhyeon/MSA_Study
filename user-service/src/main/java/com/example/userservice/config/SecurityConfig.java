package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/users/**").permitAll() // `/users` 경로 허용
						.requestMatchers("/h2-console/**").permitAll() // H2 콘솔 허용
						.anyRequest().authenticated()) // 나머지는 인증 필요
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // iframe 허용
				.formLogin(AbstractHttpConfigurer::disable); // 기본 로그인 비활성화
		return http.build();
	}
}
