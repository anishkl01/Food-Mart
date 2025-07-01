package com.foodmart.org.service;

import com.foodmart.org.io.UserRequest;
import com.foodmart.org.io.UserResponse;

public interface UserService {
	
	UserResponse register(UserRequest request);
	
	String findByUserId();
}
