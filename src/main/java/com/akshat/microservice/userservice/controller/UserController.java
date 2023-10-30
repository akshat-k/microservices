package com.akshat.microservice.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.microservice.userservice.entities.User;
import com.akshat.microservice.userservice.services.UserServices;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServices userServices;

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User createdUser = userServices.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	int count = 1;
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	@Retry(name = "ratingHotelRetry" , fallbackMethod = "ratingHotelFallback" )
	@RateLimiter(name = "ratingHotelLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		System.out.println("RETRY Count  ---->"+count++);
		User getUser = userServices.getUser(userId);
		return ResponseEntity.ok(getUser);
	}

	
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
		//System.out.println(ex.getMessage());
		System.out.println("Akshat");
		User user = User.builder().email("dummy@gmail.com").name("Mr Dummy").about("Dummy")
				.about("This about is for dummy").userId("123456789").build();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = userServices.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}
