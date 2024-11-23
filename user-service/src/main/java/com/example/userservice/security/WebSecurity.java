package com.example.userservice.security;

import com.example.userservice.service.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpSession;
import java.util.function.Supplier;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {
		private UserService userService;
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		private Environment env;

		public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
		public static final String SUBNET ="/32";
		public static final IpAddressMatcher ALLOWED_ID_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);


		public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
			this.env = env;
			this.userService = userService;
			this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		}

		@Bean
	protected SecurityFilterChain configure(HttpSecurity http, HttpSession httpSession) throws Exception {
			AuthenticationManagerBuilder authenticationManagerBuilder =
					http.getSharedObject(AuthenticationManagerBuilder.class);
			authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

			AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

			http.csrf(AbstractHttpConfigurer::disable);

			http.authorizeHttpRequests((authz) ->authz
					.requestMatchers(new AntPathRequestMatcher("actuator/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/users","POST")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/welcome")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/healthcheck")).permitAll()
					.anyRequest().authenticated()
			)
					.authenticationManager(authenticationManager)
					.sessionManagement((session) -> session
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

			http.addFilter(getAuthenticationFilter(authenticationManager));
			http.headers((headers) -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
			return http.build();
		}

		private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object){
			return new AuthorizationDecision(ALLOWED_ID_ADDRESS_MATCHER.matches(object.getRequest()));
		}



}
