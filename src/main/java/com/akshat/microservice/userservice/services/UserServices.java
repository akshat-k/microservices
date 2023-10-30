package com.akshat.microservice.userservice.services;

import java.util.List;

import com.akshat.microservice.userservice.entities.User;

public interface UserServices {

	// User Operation

	// Create
	User saveUser(User user);

	// get All User

	List<User> getAllUser();

	// Get User by Id

	User getUser(String userId);

}
