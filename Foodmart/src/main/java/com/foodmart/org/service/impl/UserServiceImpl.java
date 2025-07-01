package com.foodmart.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodmart.org.entities.UserEntity;
import com.foodmart.org.io.UserRequest;
import com.foodmart.org.io.UserResponse;
import com.foodmart.org.repositories.UserRepository;
import com.foodmart.org.service.LoggedInUser;
import com.foodmart.org.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private LoggedInUser inUser;
	

	@Override
	public UserResponse register(UserRequest request) {
	    UserEntity userEntity =	convertToEntity(request);
	    
	    UserEntity addedUserEntity = repository.save(userEntity);
	    
	    return convertToResponse(addedUserEntity);
	    
	}
	
	private UserEntity convertToEntity(UserRequest request) {
		return UserEntity.builder()
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.name(request.getName())
				.build();
	}
	
	private UserResponse convertToResponse(UserEntity entity) {
		return UserResponse.builder()
				.id(entity.getId())
				.name(entity.getName())
				.email(entity.getEmail())
				.build();
	}

	@Override
	public String findByUserId() {
		String loggedInUserEmail = inUser.getLoggedInUser().getName();
		
		UserEntity loggedInUser = repository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not found" + loggedInUserEmail));
		
		return loggedInUser.getId();
	}
	
	
}
