//package com.hcl.service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.hcl.entity.User;
//
//
//public class GroupUserDetails implements UserDetails {
//
//	private String username;
//	private String password;
//	private List<GrantedAuthority> authorities;
//	
//	public GroupUserDetails(User user) {
//		this.username = user.getUsername();
//		this.password = user.getPassword();
//
//		this.authorities = user.getRoles().stream().map(r -> r.getRoleType())
//		.map(SimpleGrantedAuthority::new)
//		.collect(Collectors.toList());
//	}
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//	
//	
//}
