package com.nskfdc.scgj.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.nskfdc.scgj.dao.LoginDao;
import com.nskfdc.scgj.dto.SessionManagementDto;

@Service
public class LoginService implements UserDetailsService{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private LoginDao loginDao;
	
	
	
	@Override
	public SessionManagementDto loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		
		LOGGER.debug("In Login Service - to get user logged in");
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		LOGGER.debug("Sending request to Login DAO - to check if user exists");
		int status = loginDao.userExistence(userEmail); 
		
		if(status == 0) {
			LOGGER.debug("In IF -- When userEmail doesn't exist");
			authorities.add(new SimpleGrantedAuthority(null));
			LOGGER.debug("Returning null");
			return new SessionManagementDto(null, null, null);
			
		}
		LOGGER.debug("In ELSE -- When userEmail does exist");
		LOGGER.debug("Sending request to loginDao");
		LOGGER.debug("To get Login details corresponding to userEmail");
		return loginDao.getValidUserDetails(userEmail);
	
	
	}
}

	
