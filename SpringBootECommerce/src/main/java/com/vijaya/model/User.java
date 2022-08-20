package com.vijaya.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user") //Table name
public class User {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UserId")
	private int id;
	
	@Column(name="UserName")
	private String name;

	@Column(name="password")
	private String password;

	@Column(name="UserEmail")
	private String email;

	
	
	}
	
	
	

	

	

	

	
