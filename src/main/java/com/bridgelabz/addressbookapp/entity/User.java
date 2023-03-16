package com.bridgelabz.addressbookapp.entity;

import com.bridgelabz.addressbookapp.dto.UserDTO;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addressbook_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private int id;

	private String fullName;
	private String address;
	private String phoneNumber;
	private String city;
	private String state;
	private String zip;
	private String email;

	public User(@Valid UserDTO userDTO) {
		this.fullName = userDTO.getFullName();
		this.address = userDTO.getAddress();
		this.phoneNumber = userDTO.getPhoneNumber();
		this.city = userDTO.getCity();
		this.state = userDTO.getState();
		this.zip = userDTO.getZip();
		this.email = userDTO.getEmail();
	}

	public User(int id2, @Valid UserDTO userDTO) {
		this.id = id2;
		this.fullName = userDTO.getFullName();
		this.address = userDTO.getAddress();
		this.phoneNumber = userDTO.getPhoneNumber();
		this.city = userDTO.getCity();
		this.state = userDTO.getState();
		this.zip = userDTO.getZip();
		this.email = userDTO.getEmail();
	}
} 