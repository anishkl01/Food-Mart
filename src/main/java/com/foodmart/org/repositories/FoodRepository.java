package com.foodmart.org.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.foodmart.org.entities.FoodEntity;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String>{
	
	
}
