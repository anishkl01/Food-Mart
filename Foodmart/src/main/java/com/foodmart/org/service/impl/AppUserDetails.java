package com.foodmart.org.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodmart.org.entities.UserEntity;
import com.foodmart.org.repositories.UserRepository;

@Service
public class AppUserDetails implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("appuserDetails" + repository.findByEmail(email));
		UserEntity user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found" + email));
		System.out.println("appuserDetails1"+user);
		
		return new User(user.getEmail(),user.getPassword() , new ArrayList<>());
	}

}
