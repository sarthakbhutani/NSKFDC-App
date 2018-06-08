package com.nskfdc.scgj.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionManagementDto implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Collection<SimpleGrantedAuthority> authorities;
	
	public SessionManagementDto(String username, String password, String userrole) {
		super();
		this.username = username;
		this.password = password;
		authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userrole));
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}


}
