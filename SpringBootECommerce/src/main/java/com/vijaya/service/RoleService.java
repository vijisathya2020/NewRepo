package com.vijaya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijaya.model.Role;
import com.vijaya.repository.RoleRepo;



	@Service
	public class RoleService {
		@Autowired
		RoleRepo repo;
		
		public List<Role> getAllRoles(){
			return repo.findAll();
		}

		public Optional<Role> getRoleById(Integer id) {
			return repo.findById(id);
		}

		public void deleteById(int id) {
			repo.deleteById(id);
		}
		
		public void addRole(Role r) {
			repo.save(r);
		}

		public void updateRole(Role r) {
			repo.save(r);
		}

		public void saveRole(Role r) {
			repo.save(r);
			
		}

		public void deleteRole(Role deleteRole) {
			repo.delete(deleteRole);
			
		}

		
	}
