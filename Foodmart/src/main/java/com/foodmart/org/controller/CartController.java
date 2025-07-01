package com.foodmart.org.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.foodmart.org.io.CartRequest;
import com.foodmart.org.io.CartResponse;
import com.foodmart.org.service.CartService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping 
	public ResponseEntity<CartResponse> addToCartHandler(@RequestBody CartRequest request) {
		String foodId = request.getFoodId();
		
		if(foodId == null || foodId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Food id not found");
		}
		
		cartService.addCart(foodId);
		
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping
	public ResponseEntity<CartResponse> getCartHandler() {
		return ResponseEntity.ok(cartService.getCart());
	}
	
	@DeleteMapping
	public ResponseEntity<?> clearCartHandler() {
		cartService.clearCart();
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/remove")
	public ResponseEntity<CartResponse> removeFoodFromCartHandler(@RequestBody CartRequest cartRequest) {
		String foodId = cartRequest.getFoodId();
		
		if(foodId == null || foodId.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(cartService.removeFoodFromCart(cartRequest));
	}
	
}
