package com.foodmart.org.service;

import java.util.List;

import com.foodmart.org.io.OrderRequest;
import com.foodmart.org.io.OrderResponse;

public interface OrderService {
	
	OrderResponse createOrder(OrderRequest orderRequest);
	
	List<OrderResponse> getUserOrder();
	
	void removeOrder(String orderId);
	
	List<OrderResponse> getOrdersOfAllUsers();
	
	void updateOrderStatus(String orderId, String orderStatus);
}
