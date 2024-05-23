package com.example.spaceship.security.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//	@Value("${security.security-realm}")
//	private String securityRealm;

	@Value("${allowed.origin.urls}")
	private String allowedOriginURLs;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(c -> corsConfigurationSource()) //AbstractHttpConfigurer::disable
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND)))
			.authorizeHttpRequests(
					c -> c						
						.requestMatchers(antMatcher("/spaceships/**")).permitAll()
						
						.requestMatchers(
								antMatcher("/swagger/**"),
								antMatcher("/swagger-ui/**"),
								antMatcher("/swagger-ui.html"),
								antMatcher("/webjars/**"),
								antMatcher("/swagger-resources/**"),
								antMatcher("/configuration/**"),
								antMatcher("/v3/api-docs/**")
								)
							.permitAll()

						.requestMatchers(antMatcher(HttpMethod.OPTIONS)).permitAll())
						.headers(headers -> headers
								.frameOptions(frameOptions -> frameOptions.sameOrigin())
								.contentSecurityPolicy(contentSecurity -> contentSecurity.policyDirectives("default-src 'self'"))
								.httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000).preload(true))

							)
					
			.build();
	}

   @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	final CorsConfiguration configuration = new CorsConfiguration();
    	configuration.setAllowedOrigins(Arrays.asList(allowedOriginURLs.split(","))); 

		configuration.setAllowedOrigins(Collections.singletonList("*"));
    	
    	configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); 
//		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList(
				"Authorization",
				"Accept",
				"Origin",
				"Cache-Control",
				"Content-Type",
				"x-csrf-token",
				"Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers",
				"X-Requested-With"
			));
			
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
