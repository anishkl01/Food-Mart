package com.foodmart.org.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodmart.org.io.OrderRequest;
import com.foodmart.org.io.OrderResponse;
import com.foodmart.org.service.OrderService;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/create")
	public ResponseEntity<OrderResponse> createOrderHandler(@RequestBody OrderRequest orderRequest) {
		
		return ResponseEntity.ok(orderService.createOrder(orderRequest));
		
	}
	
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getOrdersHanlder() {
		return ResponseEntity.ok(orderService.getUserOrder());
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> removeOrderHandler(@PathVariable String orderId) {
		orderService.removeOrder(orderId);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/all")
	public List<OrderResponse> getOrdersOfAllUsersHandler() {
		
		return orderService.getOrdersOfAllUsers();
	}
	
	@PatchMapping("/status/{orderId}")
	public ResponseEntity<Void> updateOrderStatusHandler(@PathVariable String orderId, @RequestParam String status) {
		orderService.updateOrderStatus(orderId, status);
		return ResponseEntity.ok().build();
	}
	
}
