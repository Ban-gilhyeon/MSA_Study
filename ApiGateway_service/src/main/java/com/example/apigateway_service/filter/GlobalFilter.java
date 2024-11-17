package com.example.apigateway_service.filter;

import com.netflix.spectator.impl.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	public GlobalFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//Custom Pre Filter 사전에 먼저 할 동작 정의
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse  response = exchange.getResponse();

			log.info("Global Filter  baseMSG : {}",config.getBaseMessage());

			if(config.isPreLogger()){
				log.info("Global Filter Start : request Id -> {}",request.getId());
			}
			//Custom Post Filter 이후에 할 동작 정의
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				if(config.isPostLogger()){
					log.info("Global Filter End : response Code -> {}", response.getStatusCode());
				}
			}));
		});
	}

	@Data
	public static class Config{ //yml 파일에 정의된 걸 가져다 씀
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
