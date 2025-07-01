package com.foodmart.org.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.foodmart.org.config.JwtConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtConfig config;
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		
		
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			String email = config.extractUsername(token);
			
			if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails details = detailsService.loadUserByUsername(email);
				
				if(config.validateToken(token, details)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null,details.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					
				}
				
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
