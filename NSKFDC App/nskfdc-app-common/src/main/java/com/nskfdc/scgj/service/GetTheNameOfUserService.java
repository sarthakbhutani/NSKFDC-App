package com.nskfdc.scgj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.LoginDao;

@Service
public class GetTheNameOfUserService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GetTheNameOfUserService.class);
	
	@Autowired
	private LoginDao loginDao;
	
	public String getNameOfUser(String userEmail) {
		LOGGER.debug("Request received to get the name of logged in user");
		LOGGER.debug("In GetTheNameOfUserService - getNameOfUser");
		LOGGER.debug("Sending request to LoginDao - getNameOfUser");
		return loginDao.getNameOfUser(userEmail);		
	}

}
