package com.foodmart.org.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.foodmart.org.io.FoodRequest;
import com.foodmart.org.io.FoodResponse;
import com.foodmart.org.service.FoodService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/foods")
public class FoodController {
	
	
	@Autowired
	private FoodService foodService;
	
	@PostMapping
	public FoodResponse addFood(@RequestPart("food") String food
			,@RequestPart("file") MultipartFile file) {
		
		System.out.println("Received JSON: " + food);
	    System.out.println("Received file: " + file.getOriginalFilename());
		
		ObjectMapper mapper = new ObjectMapper();
		FoodRequest request = null;
		
		try {
			 request = mapper.readValue(food, FoodRequest.class);			
			 System.out.println("Parsed Object: " + request);
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid JSON Format");
		}
		
		FoodResponse foodResponse = foodService.addFood(request, file);
		return  foodResponse;
	}
	
	@GetMapping
	public List<FoodResponse> fetchAllFoods() {
		return foodService.fetchFoods();
	}
	
	@GetMapping("/{id}")
	public FoodResponse fetchFodd(@PathVariable String id) {
		return foodService.fetchFood(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFood(@PathVariable String id) {
		 foodService.deleteFood(id);
	}
	
}
