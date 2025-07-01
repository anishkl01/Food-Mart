package com.foodmart.org.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.foodmart.org.service.LoggedInUser;

@Component
public class LoggedInUserImpl implements LoggedInUser{

	@Override
	public Authentication getLoggedInUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
