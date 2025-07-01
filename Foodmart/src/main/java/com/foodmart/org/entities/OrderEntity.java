package com.foodmart.org.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.foodmart.org.io.OrderItems;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Document(collection = "orders")
@Data
@Builder
public class OrderEntity {
	
	@Id
	private String id;
	private String userId;
	private String userAddress;
	private String phoneNumber;
	private String email;
	
	private List<OrderItems> orderedItems;
	
	private double amount;
	
	private String orderStatus;
}
