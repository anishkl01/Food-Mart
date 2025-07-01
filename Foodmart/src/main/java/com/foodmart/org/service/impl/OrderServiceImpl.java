package com.foodmart.org.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodmart.org.entities.OrderEntity;
import com.foodmart.org.io.OrderRequest;
import com.foodmart.org.io.OrderResponse;
import com.foodmart.org.repositories.OrderRepository;
import com.foodmart.org.service.OrderService;
import com.foodmart.org.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserService service;

	@Override
	public OrderResponse createOrder(OrderRequest orderRequest) {

		String loggedInUser = service.findByUserId();

		OrderEntity entity = convertToEntity(orderRequest);
		entity.setUserId(loggedInUser);
		OrderEntity savedEntity = orderRepository.save(entity);

		OrderResponse orderResponse = convertToOrderRespone(savedEntity);

		return orderResponse;

	}

	@Override
	public List<OrderResponse> getUserOrder() {
		String loggedInUser = service.findByUserId();
		List<OrderEntity> listOfOrderEntity = orderRepository.findByUserId(loggedInUser);

		return listOfOrderEntity.stream().map(orderEntity -> convertToOrderRespone(orderEntity))
				.collect(Collectors.toList());
	}

	private OrderEntity convertToEntity(OrderRequest orderRequest) {
		return OrderEntity.builder().userAddress(orderRequest.getAddress()).phoneNumber(orderRequest.getPhoneNumber())
				.email(orderRequest.getEmail()).orderedItems(orderRequest.getItems()).amount(orderRequest.getAmount())
				.orderStatus(orderRequest.getOrderStatus())
				.build();
	}

	private OrderResponse convertToOrderRespone(OrderEntity entity) {
		return OrderResponse.builder().id(entity.getId()).userId(entity.getUserId())
				.userAddress(entity.getUserAddress()).email(entity.getEmail()).phoneNumber(entity.getPhoneNumber())
				.amount(entity.getAmount()).orderStatus(entity.getOrderStatus()).items(entity.getOrderedItems())
				.build();
	}

	@Override
	public void removeOrder(String orderId) {
		
		orderRepository.deleteById(orderId);
		
	}

	@Override
	public List<OrderResponse> getOrdersOfAllUsers() {
		List<OrderEntity> list = orderRepository.findAll();
		
		return list.stream().map(entity -> convertToOrderRespone(entity)).collect(Collectors.toList());
	}

	@Override
	public void updateOrderStatus(String orderId, String orderStatus) {
		
		OrderEntity entity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found" + orderId));
		entity.setOrderStatus(orderStatus);
		
		orderRepository.save(entity);
	}

}
