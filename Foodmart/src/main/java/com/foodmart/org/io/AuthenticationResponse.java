package com.foodmart.org.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	
	private String email;
	private String token;
}
