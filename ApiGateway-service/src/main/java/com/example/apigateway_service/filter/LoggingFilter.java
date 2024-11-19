package com.example.apigateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
	public LoggingFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//Custom Pre Filter 사전에 먼저 할 동작 정의
		/*return ((exchange, chain) -> {
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
		});*/
		GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> { //ServerWebExchange exchange, GatewayFilterChain chain
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse  response = exchange.getResponse();

			log.info("Global Filter  baseMSG : {}",config.getBaseMessage());

			if(config.isPreLogger()){
				log.info("Logging Filter Start : request Id -> {}",request.getId());
			}
			//Custom Post Filter 이후에 할 동작 정의
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				if(config.isPostLogger()){
					log.info("Logging Filter POST Filter : response Code -> {}", response.getStatusCode());
				}
			}));
		}, Ordered.LOWEST_PRECEDENCE); // 우선순위 변경 최고 높게 설정한 상태
		return filter;
	}

	@Data
	public static class Config{ //yml 파일에 정의된 걸 가져다 씀
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
