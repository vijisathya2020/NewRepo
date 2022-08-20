package com.vijaya.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="role") //Table name
public class Role {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RoleId")
	private int id;
	
	@Column(name="RoleType")
	private String roleType;

	
     
	
}