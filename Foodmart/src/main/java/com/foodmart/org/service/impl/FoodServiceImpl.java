package com.foodmart.org.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.foodmart.org.entities.FoodEntity;
import com.foodmart.org.io.FoodRequest;
import com.foodmart.org.io.FoodResponse;
import com.foodmart.org.repositories.FoodRepository;
import com.foodmart.org.service.FoodService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service

public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private S3Client client;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Value("${s3.bucketname}")
	private String bucketName;
	
	@Override
	public String uploadFile(MultipartFile file) {
		
		String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String key = UUID.randomUUID().toString()+"."+fileExtension;
		
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().
					bucket(bucketName)
					.key(key)
					.acl("public-read")
					.contentType(file.getContentType())
					.build();
			PutObjectResponse objectResponse = client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
			
			if(objectResponse.sdkHttpResponse().isSuccessful()) {
				return "https://"+bucketName+".s3.amazonaws.com/"+key;
			} else {
				System.out.println("eroor");
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File upload failed.");
			}
			
		} catch (IOException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occured while uploading file");
		}
	}

	@Override
	public FoodResponse addFood(FoodRequest foodRequest, MultipartFile file) {
		FoodEntity newFoodEntity =  convertToFoodEntity(foodRequest);
		String imageUrl = uploadFile(file);
		newFoodEntity.setImageUrl(imageUrl);
		newFoodEntity = foodRepository.save(newFoodEntity);
		FoodResponse newFoodResponse = convertToFoodResponse(newFoodEntity);
		return newFoodResponse;
	}
	
	private FoodEntity convertToFoodEntity(FoodRequest foodRequest) {
		return FoodEntity.builder()
		.name(foodRequest.getName())
		.description(foodRequest.getDescription())
		.category(foodRequest.getCategory())
		.price(foodRequest.getPrice())
		.build();
	}
	
	private FoodResponse convertToFoodResponse(FoodEntity entity) {
		return FoodResponse.builder()
				.id(entity.getId())
				.name(entity.getName())
				.category(entity.getCategory())
				.description(entity.getDescription())
				.price(entity.getPrice())
				.imageUrl(entity.getImageUrl())
				.build();
	}

	@Override
	public List<FoodResponse> fetchFoods() {
		List<FoodEntity> foodEntities = foodRepository.findAll();
		return foodEntities.stream().map(food -> convertToFoodResponse(food)).collect(Collectors.toList());
	}

	@Override
	public FoodResponse fetchFood(String id) {
		FoodEntity existingFoodEntity = foodRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Food not found for the id: "+id));
		
		return convertToFoodResponse(existingFoodEntity);
		
	}

	@Override
	public boolean deleteFile(String filename) {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(filename)
				.build();
		
		client.deleteObject(deleteObjectRequest);
		return true;
	}

	@Override
	public void deleteFood(String id) {
		FoodResponse foodResponse = fetchFood(id);
		
		String imageUrl = foodResponse.getImageUrl();
		
		String filename = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		
		boolean isFileDeleted = deleteFile(filename);
		
		if(isFileDeleted)
			foodRepository.deleteById(foodResponse.getId());
		
	}
	
	
	
	

}
