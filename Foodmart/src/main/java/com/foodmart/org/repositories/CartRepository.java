package com.foodmart.org.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foodmart.org.entities.CartEntity;

public interface CartRepository extends MongoRepository<CartEntity, String> {
	
	Optional<CartEntity> findByUserId(String id);
	
	void deleteByUserId(String id);
}
