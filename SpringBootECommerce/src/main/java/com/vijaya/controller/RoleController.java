package com.vijaya.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vijaya.model.Role;
import com.vijaya.service.RoleService;





@RestController
public class RoleController {
	@Autowired
	private RoleService service;

	
	
	@GetMapping("roles")
	public List<Role> listAllRoles() {
		return service.getAllRoles();

	}
	
	@GetMapping("/roles/{id}") // Retrieving the records
	public Optional<Role> getRoleID(@PathVariable Integer id) {
		Optional<Role> r=service.getRoleById(id);
		return service.getRoleById(id);

	}

	@PostMapping("/roles/save") // Inserting the record
	public String addRole(@RequestBody Role r) {
		service.addRole(r);
		return "Saved...";
	}

		
	@PutMapping("roles/update/{id}") // Updating the record
	public String updateRole(@PathVariable Integer id, @RequestBody Role r) {
		Role updatedRole = service.getRoleById(id).get();
		updatedRole.setRoleType(r.getRoleType());
		service.saveRole(updatedRole);
		return "Updated ...";
	}
	
	@DeleteMapping("roles/delete/{id}") // Deleting the record
	public String deleteRole(@PathVariable Integer id) {
		Role deleteRole =service.getRoleById(id).get();
		service.deleteRole(deleteRole);
		return "Delete user with this role id: "+id; 
	}

	

	}

		
	

