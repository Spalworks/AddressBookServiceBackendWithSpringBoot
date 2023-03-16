package com.bridgelabz.addressbookapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.addressbookapp.dto.UserDTO;
import com.bridgelabz.addressbookapp.entity.User;
import com.bridgelabz.addressbookapp.exception.AddressBookException;
import com.bridgelabz.addressbookapp.repository.AddressBookRepo;
import com.bridgelabz.addressbookapp.util.TokenUtil;

import jakarta.validation.Valid;

@Service
public class AddressBookService implements IAddressBookService {

	@Autowired
	AddressBookRepo repository;

	@Autowired
	TokenUtil tokenUtil;

	@Override
	public List<User> getAllUser() {
		List<User> response = repository.findAll();
		if (response.size() == 0)
			throw new AddressBookException("No user found!!!");

		return response;
	}

	@Override
	public User getUserById(int userId) {
		Optional<User> response = repository.findById(userId);
		return response.orElseThrow(() -> new AddressBookException("User not found with id : " + userId));
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> response = repository.findByEmail(email);
		return response.orElseThrow(() -> new AddressBookException("User not found with email : " + email));
	}

	@Override
	public User createNewUser(@Valid UserDTO userDTO) {
		User userData = new User(userDTO);
		repository.save(userData);
		return userData;
	}

	@Override
	public User UpdateUser(int id, @Valid UserDTO userDTO) {
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			User userData = new User(id, userDTO);
			repository.save(userData);
			return userData;
		} else
			throw new AddressBookException("User not found!!!");
	}

	@Override
	public void deleteUserById(int id) {
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			repository.deleteById(id);
		} else
			throw new AddressBookException("User not found!!!");

	}

	@Override
	public String createNewUserByToken(@Valid UserDTO userDTO) {
		User userData = new User(userDTO);
		repository.save(userData);
		String token = tokenUtil.createToken(userData.getId());
		return token;
	}

	@Override
	public User UpdateUser(String token, @Valid UserDTO userDTO) {
		int id = tokenUtil.decodeToken(token);
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			User userData = new User(id, userDTO);
			repository.save(userData);
			return userData;
		} else
			throw new AddressBookException("User not found!!!");
	}

	@Override
	public User getUserDataByToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> response = repository.findById(userId);
		return response.orElseThrow(() -> new AddressBookException("User not found with id : " + userId));
	}

	@Override
	public int deleteUserDataByToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> existUser = repository.findById(userId);
		if (existUser.isPresent()) {
			repository.deleteById(userId);
		} else
			throw new AddressBookException("User not found!!!");
		
		return userId;
	}

}