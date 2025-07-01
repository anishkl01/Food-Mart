package com.foodmart.org.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtConfig {
	
	@Value("${jwt.secret.key}")
	private String SECRET_KEY;
	
	
	public String generateToken(UserDetails details) {
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims,details.getUsername());
	}
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(subject)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
		.signWith(getSigningKey(),SignatureAlgorithm.HS256)
		.compact();
	}
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claim = extractAllClaims(token);
		
		return claimsResolver.apply(claim);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails details) {
		final String username = extractUsername(token);
		
		return (username.equals(details.getUsername()) && !isTokenExpired(token));
	}
	
}
