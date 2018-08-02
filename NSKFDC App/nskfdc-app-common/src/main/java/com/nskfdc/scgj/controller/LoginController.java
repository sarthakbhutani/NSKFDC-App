package com.nskfdc.scgj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.LoginDto;
import com.nskfdc.scgj.dto.SessionManagementDto;

@RestController
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	
	@RequestMapping("/user")
	public Principal loginDetailsAuthentication(Principal user){
		
		LOGGER.debug("In LoginController, to get the user authenticated");
		LOGGER.debug("Request received from the frontend for authorised login");
		
		try {
			
			LOGGER.debug("TRYING -- to get user authorized");
			
			return user;
			
			
		}catch(Exception e) {
			LOGGER.debug("CATCHING -- Exception in LoginControlller");
			LOGGER.error("In method - loginDetailsAuthentication");
			LOGGER.error("Exception is :"+e);
			LOGGER.debug("returning NULL");
			return null;
		}
	}


	}
