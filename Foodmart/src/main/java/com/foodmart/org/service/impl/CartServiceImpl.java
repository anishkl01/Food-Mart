package com.foodmart.org.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodmart.org.entities.CartEntity;
import com.foodmart.org.io.CartRequest;
import com.foodmart.org.io.CartResponse;
import com.foodmart.org.repositories.CartRepository;
import com.foodmart.org.service.CartService;
import com.foodmart.org.service.UserService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private UserService service;
	
	@Autowired
	private CartRepository cartRepository;
	
	public CartResponse addCart(String foodId) {
		String loggedInUser = service.findByUserId();
		
		Optional<CartEntity> cartEntity = cartRepository.findByUserId(loggedInUser);
		
		CartEntity newCartEntity = cartEntity.orElseGet(() -> new CartEntity(loggedInUser, new HashMap<String,Integer>()));
		
		Map<String, Integer> cartItems = newCartEntity.getItems();
		
		cartItems.put(foodId, cartItems.getOrDefault(foodId, 0) + 1);
		
		newCartEntity.setItems(cartItems);
		
		CartEntity savedCartEntity = cartRepository.save(newCartEntity);
		
		return convertToResponse(savedCartEntity);
		
		
	}
	
	
	private CartResponse convertToResponse(CartEntity cartEntity) {
		return CartResponse.builder()
				.id(cartEntity.getId())
				.userId(cartEntity.getUserId())
				.items(cartEntity.getItems())
				.build();
	}


	@Override
	public CartResponse getCart() {
		String loggedInUserId = service.findByUserId();
		
		CartEntity cartEntity = cartRepository.findByUserId(loggedInUserId)
			.orElse(new CartEntity(null,loggedInUserId, new HashMap<>()));
		
		return convertToResponse(cartEntity);
		
	}


	@Override
	public void clearCart() {
		String loggedInUserId = service.findByUserId();
		
		cartRepository.deleteByUserId(loggedInUserId);
	}


	@Override
	public CartResponse removeFoodFromCart(CartRequest cartRequest) {
		String loggedInUserId = service.findByUserId();
		
		CartEntity cartEntity = cartRepository.findByUserId(loggedInUserId)
				.orElseThrow(() -> new RuntimeException("Cart not found"));
		
		Map<String, Integer> cartItems = cartEntity.getItems();
		
		if(cartItems.containsKey(cartRequest.getFoodId())) {
			int currentQty = cartItems.get(cartRequest.getFoodId());
			
			if(currentQty > 0) {
				cartItems.put(cartRequest.getFoodId(),currentQty - 1);
			} else {
				cartItems.remove(cartRequest.getFoodId());
			}
			
			cartRepository.save(cartEntity);
		}
		
		return convertToResponse(cartEntity);
	}

}
