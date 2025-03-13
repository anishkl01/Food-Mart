package com.foodmart.org.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Foods")
@Builder
public class FoodEntity {
	@Id
	private String id;
	private String name;
	private String description;
	private String imageUrl;
	private double price;
	private String category;
}
