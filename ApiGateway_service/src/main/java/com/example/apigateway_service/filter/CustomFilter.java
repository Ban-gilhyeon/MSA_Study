package com.example.apigateway_service.filter;

import com.netflix.spectator.impl.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	public CustomFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//Custom Pre Filter 사전에 먼저 할 동작 정의
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse  response = exchange.getResponse();

			log.info("Custom PRE filter: request Id -> {}", request.getId());

			//Custom Post Filter 이후에 할 동작 정의
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("Custom POST filter: response Id -> {}", response.getStatusCode());
			}));
		});
	}

	public static class Config{
		// Put the configuration properties
	}
}
