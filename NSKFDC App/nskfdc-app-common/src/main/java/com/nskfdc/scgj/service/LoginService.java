package com.nskfdc.scgj.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.LoginDao;
import com.nskfdc.scgj.dto.SessionManagementDto;

@Service
public class LoginService implements UserDetailsService{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private LoginDao loginDao;
	
	
	
	@Override
	public SessionManagementDto loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		int status = loginDao.userExistence(username); 
		if(status == 0) {
			
			authorities.add(new SimpleGrantedAuthority(null));
			return new SessionManagementDto(null, null, null);
			
		}
		
		return loginDao.getValidUserDetails(username);
	
	}
	

}
