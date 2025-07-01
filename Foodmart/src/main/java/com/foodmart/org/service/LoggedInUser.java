package com.foodmart.org.service;

import org.springframework.security.core.Authentication;

public interface LoggedInUser {
	
	Authentication getLoggedInUser();
}
