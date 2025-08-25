package com.harsh.LibraryManagement.configuratio;

import org.springframework.security.config.Customizer;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class Config {
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.cors(Customizer.withDefaults()).
	                csrf(AbstractHttpConfigurer::disable).
	                authorizeHttpRequests(auth-> auth.anyRequest().permitAll()).
	                sessionManagement( session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	        return http.build();
	    }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	  }
	 
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000")); // allow all origins
	        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // allowed methods
	        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // allowed headers
	        configuration.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);

	        return source;
	    }
}

