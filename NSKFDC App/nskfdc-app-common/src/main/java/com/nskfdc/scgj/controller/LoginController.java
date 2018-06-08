package com.nskfdc.scgj.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		
		LOGGER.debug("In LoginController, to get the authenticated user details");
		LOGGER.debug("Request received from the frontend for authorised login");
		
		try {
			
			LOGGER.debug("In TRY block of LoginController");
			
			return user;
			
			
		}catch(Exception e) {
			LOGGER.debug("In CATCH block of LoginControlller");
			LOGGER.error("ERROR: Encountered an exception.");
			LOGGER.error("Exception is :"+e);
			LOGGER.debug("returning NULL");
			return user;
		}
	}

}
