package com.vijaya.controller;
import com.vijaya.model.User;
import com.vijaya.repository.UserRepo;
import com.vijaya.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/")
	public String homePage() {
		return "Welcome HOME !!!";
	}
	
		
	@GetMapping("/admin")
	public String adminPage() {
		return "Welcome to the shopping page as ADMIN !!!";
	}
	
	@GetMapping("/user")
	public String userPage() {
		return "Welcome to the shopping page as USER !!!";
	}
	
	@GetMapping("/users")
	public List<User> listAllUsers() {
		return service.getAllUsers();

	}

	@GetMapping("/users/{id}") // Retrieving the records
	public Optional<User> getUserID(@PathVariable Integer id) {
		Optional<User> u=service.getUserById(id);
		return service.getUserById(id);

	}

	@PostMapping("users/save") // Inserting the record
	public String addUser(@RequestBody User u) {
		service.addUser(u);
		return "Saved...";
	}
	
	@PutMapping("users/update/{id}") // Updating the record
	public String updateUser(@PathVariable Integer id, @RequestBody User u) {
		User updatedUser = service.getUserById(id).get();
		updatedUser.setName(u.getName());
		updatedUser.setPassword(u.getPassword());
		updatedUser.setEmail(u.getEmail());
		service.saveUser(updatedUser);
		return "Updated...";
	}
	
	@DeleteMapping("users/delete/{id}") // Deleting the record
	public String deleteUser(@PathVariable Integer id) {
		User deleteUser =service.getUserById(id).get();
		service.deleteUser(deleteUser);
		return "Delete user with the id: "+id; 
	}

}


