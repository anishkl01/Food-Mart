package com.foodmart.org.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.foodmart.org.entities.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
	
	Optional<UserEntity> findByEmail(String email);
	
}
