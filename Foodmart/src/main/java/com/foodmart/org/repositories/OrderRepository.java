package com.foodmart.org.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.foodmart.org.entities.OrderEntity;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {
	
	List<OrderEntity> findByUserId(String userId);
}
