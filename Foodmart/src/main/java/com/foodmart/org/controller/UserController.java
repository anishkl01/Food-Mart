package com.foodmart.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodmart.org.io.UserRequest;
import com.foodmart.org.io.UserResponse;
import com.foodmart.org.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerHandler(@RequestBody UserRequest request) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.register(request));
		
	}
}
