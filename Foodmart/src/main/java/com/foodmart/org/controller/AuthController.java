package com.foodmart.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodmart.org.config.JwtConfig;
import com.foodmart.org.io.AuthenticationRequest;
import com.foodmart.org.io.AuthenticationResponse;
import com.foodmart.org.service.impl.AppUserDetails;


@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AppUserDetails appUserDetails;
	
	@Autowired
	private JwtConfig config;
	
	@PostMapping("/login")
	public AuthenticationResponse authenticationResponse(@RequestBody AuthenticationRequest authenticationRequest) {
		System.out.println("jwt hello");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
			
		} catch (Exception e) {
			System.out.println("Authentication failed: " + e.getMessage());
	        throw new RuntimeException("Invalid email or password");
		}
		final UserDetails details = appUserDetails.loadUserByUsername(authenticationRequest.getEmail());
		System.out.println("jwt hello1" + details);
		final String jwtToken = config.generateToken(details);
		System.out.println("jwt hello2" + jwtToken);
		return AuthenticationResponse.builder()
				.email(authenticationRequest.getEmail())
				.token(jwtToken).build();
		
	}
}
