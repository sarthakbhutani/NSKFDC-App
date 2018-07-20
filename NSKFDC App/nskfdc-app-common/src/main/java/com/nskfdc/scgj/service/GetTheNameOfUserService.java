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
		LOGGER.debug("In service: GetTheNameOfUser, to get name of User");
		return loginDao.getNameOfUser(userEmail);		
	}

}
