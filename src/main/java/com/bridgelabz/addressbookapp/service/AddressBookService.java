package com.bridgelabz.addressbookapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.addressbookapp.dto.UserDTO;
import com.bridgelabz.addressbookapp.entity.User;
import com.bridgelabz.addressbookapp.exception.AddressBookException;
import com.bridgelabz.addressbookapp.repository.AddressBookRepo;
import com.bridgelabz.addressbookapp.util.EmailService;
import com.bridgelabz.addressbookapp.util.TokenUtil;

@Service
public class AddressBookService implements IAddressBookService {

	@Autowired
	AddressBookRepo repository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	EmailService emailService;

	
	//get all the user details present in the AddressBook
	@Override
	public List<User> getAllUser() {
		List<User> response = repository.findAll();
		if (response.size() == 0)
			throw new AddressBookException("No user found!!!");

		return response;
	}

	
	//getting data of a particular user by using the ID of that user
	@Override
	public User getUserById(int userId) {
		Optional<User> response = repository.findById(userId);
		return response.orElseThrow(() -> new AddressBookException("User not found with id : " + userId));
	}


	//getting data of a particular user by using the emailID of that user
	@Override
	public User getUserByEmail(String email) {
		Optional<User> response = repository.findByEmail(email);
		return response.orElseThrow(() -> new AddressBookException("User not found with email : " + email));
	}

	
	//creating a new user by sending the datas in the Body
	@Override
	public User createNewUser(UserDTO userDTO) {
		User userData = new User(userDTO);
		repository.save(userData);
		return userData;
	}

	
	//Updating the data of a existing user using UserId
	@Override
	public User UpdateUser(int id, UserDTO userDTO) {
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			User userData = new User(id, userDTO);
			repository.save(userData);
			return userData;
		} else
			throw new AddressBookException("User not found!!!");
	}

	
	//Delete the data of a existing User using UserId
	@Override
	public void deleteUserById(int id) {
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			repository.deleteById(id);
		} else
			throw new AddressBookException("User not found!!!");

	}
	
	
	//creating a new user by sending the datas in the Body & generating token using JWT
	//After token creation user will get a Welcome email with a link through which user will get the User Data 
	@Override
	public String createNewUserByToken(UserDTO userDTO) {
		User userData = new User(userDTO);
		repository.save(userData);
		String token = tokenUtil.createToken(userData.getId());

		emailService.sendEmail(userData.getEmail(), "WELCOME",
				"Hi " + userData.getFullName() + " !!! Welcome to AddressBook Service App of Sourav Pal." + "\n"
						+ "User data is created successfully. To check the User data please click on the link below: "
						+ "\n" + "http://localhost:8080/addressbookservice/get-user-by-token/" + token);

		return token;
	}

	
	//Updating user Data by sending the datas in the Body & then generating token using JWT
	//After token creation user will get a email(Update notification) with a link through which user will get the updated User Data 
	@Override
	public User UpdateUser(String token, UserDTO userDTO) {
		int id = tokenUtil.decodeToken(token);
		Optional<User> existUser = repository.findById(id);
		if (existUser.isPresent()) {
			User userData = new User(id, userDTO);
			repository.save(userData);
			String newToken = tokenUtil.createToken(userData.getId());
			
			emailService.sendEmail(userData.getEmail(), "Update",
					"Hi " + userData.getFullName() + " !!!" + "\n"
							+ "User data is updated successfully. To check the User data please click on the link below: "
							+ "\n" + "http://localhost:8080/addressbookservice/get-user-by-token/" + newToken);
			
			return userData;
		} else
			throw new AddressBookException("User not found!!!");
	}

	
	//getting data of a particular user by using the token(generated at the time of Creation/Updation of User data)
	@Override
	public User getUserDataByToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> response = repository.findById(userId);
		return response.orElseThrow(() -> new AddressBookException("User not found with id : " + userId));
	}

	
	//Delete the data of a existing User using the token(generated at the time of Creation/Updation of User data)
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