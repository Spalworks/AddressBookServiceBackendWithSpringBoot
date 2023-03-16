package com.bridgelabz.addressbookapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.addressbookapp.dto.ResponseDTO;
import com.bridgelabz.addressbookapp.dto.UserDTO;
import com.bridgelabz.addressbookapp.entity.User;
import com.bridgelabz.addressbookapp.service.IAddressBookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/addressbookservice")
public class AddressBookController {

	@Autowired
	private IAddressBookService service;

	
	
	@GetMapping(value = { "", "/", "/getAll" })
	public ResponseEntity<ResponseDTO> getAddressbookData() {
		List<User> result = service.getAllUser();
		ResponseDTO responseDTO = new ResponseDTO(result, "Data retrived Successfully");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}

	
	@GetMapping(value = "/getbyid/{userId}")
	public ResponseEntity<ResponseDTO> getUserById(@PathVariable int userId) {
		User result = service.getUserById(userId);
		ResponseDTO responseDTO = new ResponseDTO(result, "Data retrived Successfully");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}

	
	@GetMapping(value = "/getbyemail/{email}")
	public ResponseEntity<ResponseDTO> getUserByMail(@PathVariable String email) {
		User result = service.getUserByEmail(email);
		ResponseDTO responseDTO = new ResponseDTO(result, "Data retrived Successfully");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
 
	
	@PostMapping(value = "/createuser")
	public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		User result = service.createNewUser(userDTO);
		ResponseDTO responseDTO = new ResponseDTO(result, "Userdata created Successfully");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}

	
	@PutMapping(value = "/updateuser/{userId}")
	public ResponseEntity<ResponseDTO> updateUserById(@PathVariable("userId") int id,
			@Valid @RequestBody UserDTO userDTO) {

		User result = service.UpdateUser(id, userDTO);
		ResponseDTO responseDTO = new ResponseDTO(result, "Userdata updated Successfully");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	
	@DeleteMapping(value = "/deleteuser/{id}")
	public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable int id) {
		service.deleteUserById(id);
		ResponseDTO responseDTO = new ResponseDTO("Deleted Data Successfully", "Deleted id: " + id);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	
	@PostMapping(value = "/create-user-by-token")
	public ResponseEntity<ResponseDTO> createUserByToken(@Valid @RequestBody UserDTO userDTO) {
		String token = service.createNewUserByToken(userDTO);
		ResponseDTO responseDTO = new ResponseDTO(token, "Userdata created Successfully with JWT token");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update-user-by-token/{token}")
	public ResponseEntity<ResponseDTO> updateUserByToken(@PathVariable String token,
			@Valid @RequestBody UserDTO userDTO){
		
		User result = service.UpdateUser(token, userDTO);
		ResponseDTO responseDTO = new ResponseDTO(result, "Userdata updated Successfully with token verification");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/get-user-by-token/{token}")
	public ResponseEntity<ResponseDTO> getUserByToken(@PathVariable String token) {
		User result = service.getUserDataByToken(token);
		ResponseDTO responseDTO = new ResponseDTO(result, "Data retrived Successfully using token");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	@DeleteMapping(value = "/delete-user-by-token/{token}")
	public ResponseEntity<ResponseDTO> deleteUserByToken(@PathVariable String token) {
		int deletedUserId = service.deleteUserDataByToken(token);
		ResponseDTO responseDTO = new ResponseDTO("Deleted id: " + deletedUserId, "Deleted User Data Successfully");

		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
}