package com.bridgelabz.addressbookapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.addressbookapp.entity.User;

@Repository
public interface AddressBookRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}