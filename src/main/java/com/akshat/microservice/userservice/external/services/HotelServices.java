package com.akshat.microservice.userservice.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.akshat.microservice.userservice.entities.Hotel;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServices {

	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable("hotelId") String hotelId);
	
}
