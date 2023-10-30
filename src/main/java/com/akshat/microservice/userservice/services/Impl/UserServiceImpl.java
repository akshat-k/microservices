package com.akshat.microservice.userservice.services.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.akshat.microservice.userservice.entities.Hotel;
import com.akshat.microservice.userservice.entities.Ratings;
import com.akshat.microservice.userservice.entities.User;
import com.akshat.microservice.userservice.exception.ResourceNotFoundException;
import com.akshat.microservice.userservice.external.services.HotelServices;
import com.akshat.microservice.userservice.repositories.UserRepository;
import com.akshat.microservice.userservice.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HotelServices hotelServices;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		// Get user from database
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());

		// Get ratings of user by calling rating service to get the rating by userId
		// http://localhost:8083/ratings/users/8827c49b-1fdc-4319-b5bc-77e01e3231b3
		Ratings[] ratingOfUser = restTemplate
				.getForObject("http://RATING-SERVICE:8083/ratings/users/" + user.getUserId(), Ratings[].class);

		List<Ratings> ratings = Arrays.stream(ratingOfUser).toList();
		List<Ratings> ratingList = ratings.stream().map(rating -> {

			// Below is the example of Rest Template for call different service
			// ResponseEntity<Hotel> forEntity = restTemplate
			// .getForEntity("http://HOTEL-SERVICE:8082/hotels/" + rating.getHotelId(),
			// Hotel.class);
			// Hotel hotel = forEntity.getBody();

			// Now calling via FeignClient i.e. hotelservice object is used to call hotel
			// service via application name
			// For more info check UserServiceImpl
			Hotel hotel = hotelServices.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);

		return user;

	}

}
