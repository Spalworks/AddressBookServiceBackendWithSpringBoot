package com.bridgelabz.addressbookapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.addressbookapp.dto.UserDTO;
import com.bridgelabz.addressbookapp.entity.User;

@Service
public interface IAddressBookService {
	
	List<User> getAllUser();
	
	User getUserById(int userId);

	User getUserByEmail(String email);

	User createNewUser(UserDTO userDTO);

	User UpdateUser(int id, UserDTO userDTO);

	void deleteUserById(int id);

	String createNewUserByToken(UserDTO userDTO);

	User UpdateUser(String token, UserDTO userDTO);

	User getUserDataByToken(String token);

	int deleteUserDataByToken(String token);

	

	

	

	

}