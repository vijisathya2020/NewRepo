package com.vijaya.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijaya.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	

	

}
