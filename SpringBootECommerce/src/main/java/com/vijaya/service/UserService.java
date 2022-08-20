package com.vijaya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijaya.model.User;
import com.vijaya.repository.UserRepo;


@Service
public class UserService {
	@Autowired
	UserRepo repo;
	
	public List<User> getAllUsers(){
		return repo.findAll();
	}

	public Optional<User> getUserById(Integer id) {
		return repo.findById(id);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	public void addUser(User u) {
		repo.save(u);
	}

	public void updateUser(User u) {
		repo.save(u);
	}

	public void saveUser(User u) {
		repo.save(u);
		
	}

	public void deleteUser(User deleteUser) {
		repo.delete(deleteUser);
		
	}

	
}
