package com.nskfdc.scgj.dto;

import java.util.ArrayList;
import java.util.Collection;


import com.nskfdc.scgj.common.BaseDto;

public class LoginDto extends BaseDto{
	
	private String username;
	private String password;
	
	
	public LoginDto(String username, String password, String userRole) {
		super();
		this.username = username;
		this.password = password;
		
		
	}
	
	public LoginDto() {}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
