package com.example.apigateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//yml에 동일한 내용이 있기 때문에 주석처리
//@Configuration
public class FilterConfig {
	//@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes() // yml에 있는 cloud: routes: 내용과 동일한 내용임
				.route(r -> r.path("/first_service/**")
						.filters(f -> f.addRequestHeader("first_request","first_request_header")
								.addResponseHeader("first_response","first_response_header"))
						.uri("http://localhost:8081"))
				.route(r -> r.path("/second_service/**")
						.filters(f->f.addRequestHeader("second_request","second_request_header")
								.addResponseHeader("second_response","second_response_header"))
						.uri("http://localhost:8082"))
				.build();
	}
}
