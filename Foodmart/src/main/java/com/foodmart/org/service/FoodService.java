package com.foodmart.org.service;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.foodmart.org.io.FoodRequest;
import com.foodmart.org.io.FoodResponse;

public interface FoodService {
//dd
	String uploadFile(MultipartFile file);
	
	FoodResponse addFood(FoodRequest foodRequest, MultipartFile file);
	
	List<FoodResponse> fetchFoods();
	
	FoodResponse fetchFood(String id);
	
	boolean deleteFile(String filename);
	
	void deleteFood(String id);
}
