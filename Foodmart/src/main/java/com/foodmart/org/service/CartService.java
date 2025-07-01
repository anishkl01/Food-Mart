package com.foodmart.org.service;

import com.foodmart.org.io.CartRequest;
import com.foodmart.org.io.CartResponse;

public interface CartService {
	
	CartResponse addCart(String foodId);
	
	CartResponse getCart();
	
	void clearCart();
	
	CartResponse removeFoodFromCart(CartRequest cartRequest);
}
