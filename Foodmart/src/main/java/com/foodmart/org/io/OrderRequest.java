package com.foodmart.org.io;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
	
	private String userId;
	private List<OrderItems> items;
	private String phoneNumber;
	private String email;
	private String address;
	private double amount;
	private String orderStatus;
}
