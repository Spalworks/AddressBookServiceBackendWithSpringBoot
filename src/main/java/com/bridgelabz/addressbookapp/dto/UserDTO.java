package com.bridgelabz.addressbookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@NotBlank
	@Pattern(regexp = "^[A-Z]{1}[A-Za-z]{2,}$")
	private String fullName;
	
	@NotBlank(message = "Address should not be empty!")
	private String address;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{10}$", message = "PhoneNumber should not be empty!")
	private String phoneNumber;
	
	@NotBlank(message = "City should not be empty!")
	private String city;
	
	@NotBlank(message = "State should not be empty!")
	private String state;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{6}$", message = "Zip should not be empty!")
	private String zip;
	
	@NotBlank(message = "Email should not be empty!")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
	private String email;
}