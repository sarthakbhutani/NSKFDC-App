package com.nskfdc.scgj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.GetTheNameOfUserDto;
import com.nskfdc.scgj.service.GetTheNameOfUserService;

@RestController
public class GetNameOfUserController {
	
	@Autowired
	private GetTheNameOfUserService getTheNameOfUserService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GetNameOfUserController.class);
	
	@RequestMapping("/getNameOfUser")
	public GetTheNameOfUserDto getNameOfUser() {
		
		
		String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
		LOGGER.debug("Trying to get name for user" + userEmail);
		String trainingPartnerName= getTheNameOfUserService.getNameOfUser(userEmail);
		GetTheNameOfUserDto getNameDto = new GetTheNameOfUserDto(trainingPartnerName);
		return getNameDto;
	}


}
