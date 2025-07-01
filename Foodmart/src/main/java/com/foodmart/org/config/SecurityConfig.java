package com.foodmart.org.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.foodmart.org.filter.JwtFilter;
import com.foodmart.org.service.impl.AppUserDetails;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private AppUserDetails appUserDetails;
	
	@Autowired
	private JwtFilter filter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/register","/api/login","/api/foods/**","api/orders/all","api/orders/status/**").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class)
				.build();
							
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter(corsConfigurationSource());
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(appUserDetails);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return new ProviderManager(authenticationProvider);
	}
	
	private UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5174"));
		cors.setAllowedMethods(List.of("POST","GET","PUT","DELETE","OPTIONS","PATCH"));
		cors.setAllowedHeaders(List.of("Authorization","Content-Type"));
		cors.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		basedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
		return basedCorsConfigurationSource;
	}
}
