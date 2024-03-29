package com.akshat.microservice.userservice.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	@Column(name = "Id")
	private String userId;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "about")
	private String about;

	@Transient
	private List<Ratings> ratings = new ArrayList<>();

}
