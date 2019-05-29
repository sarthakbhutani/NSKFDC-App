package com.nskfdc.scgj.service;

import java.util.Collection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.GenerateCredentialDao;
import com.nskfdc.scgj.dto.GenerateCredentialDto;
import com.nskfdc.scgj.dto.GenerateCredentialSearchDto;

@Service
public class GenerateCredentialService {
private static final Logger LOGGER= LoggerFactory.getLogger(GenerateCredentialService.class);
	
	@Autowired
	private GenerateCredentialDao generatecredentialDao;

	
	

	public Integer checkUserExistence(GenerateCredentialDto generateCredentialDto)
	{
		LOGGER.debug("Request received in service from controller");
		LOGGER.debug("In method - checkUserExistence");
		LOGGER.debug("To generate Credentials");
		Integer userExistenceStatus = -25;
		
		LOGGER.debug("Sending Request to Generate Credential DAO");
		LOGGER.debug("To Check, if email id already exists or not");
		Integer  userExistence = generatecredentialDao.checkUserExistence(generateCredentialDto.getUserEmail());
	  	
		LOGGER.debug("Sending Request to Generate Credential DAO");
		LOGGER.debug("To Check, if nsdc Registration Number already exists or not");
		Integer  nsdcRegNumberExistence = generatecredentialDao.checkNsdcNumberExistence(generateCredentialDto.getNsdcRegNumber());
		
	    if(userExistence == 0 && nsdcRegNumberExistence == 0)
	    {
	    	LOGGER.debug("In IF - When user doesn't exist(both email id & nsdc Reg Number)");
	    	String role = "TP";
	    	LOGGER.debug("Calling method to generate new Credentials - getGenerateCredentialService");
	    	LOGGER.debug("Passing the details from Frontend & role of user= TP ");
	    	return getGenerateCredentialService(generateCredentialDto,role);
	    	}
	    
	    else
	    {
	    	LOGGER.debug("In ELSE - User Already exists");
	    	LOGGER.debug("Returning status code -25");
	    	return userExistenceStatus;
	    }
	    
	    
	}
	
	
	
	public Integer getGenerateCredentialService(GenerateCredentialDto generateCredentialDto,String role)
	{
		LOGGER.debug("When user does not exists");
		LOGGER.debug("In method - getGenerateCredentialService");
		LOGGER.debug("To generate user Credentials");
			
		Integer userCredentialStatus = - 10;
		LOGGER.debug("Sending request to Generate Credential DAO");
		LOGGER.debug("To generate new credentials in user Table");
		userCredentialStatus = generatecredentialDao.generateCredential(generateCredentialDto, role);
		
		if(userCredentialStatus == 1)
		{	
			LOGGER.debug("IN IF -- When credentials are successfully stored in User Table");
			LOGGER.debug("Sending Request to Generate Credential DAO");
			LOGGER.debug("To insert other details of Training Partner");
			return generatecredentialDao.inputTrainingPartnerDetails(generateCredentialDto);	
		}
		
		else
		{
			LOGGER.debug("In ELSE -- When credentials are not stored in User Table");
			LOGGER.debug("Returning status code -10");
			return userCredentialStatus;
		}
	
	}

	public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(String nsdcRegNumber){
		
		LOGGER.debug("Request received from Controller in getTrainingPartnerDetail");
		LOGGER.debug("To get Training Partner credential details for searched NSDC Reg Number ");
		
		try {
			LOGGER.debug("TRYING -- getTrainingPartnerDetail");
			LOGGER.debug("Sending Request to Generate Credentials DAO-getTrainingPartnerDetail");
			return generatecredentialDao.getTrainingPartnerDetail(nsdcRegNumber);
		} catch (Exception e) {
			LOGGER.error("CATCHING -- Exception handled in Generate Credentials Service");
			LOGGER.error("Exception in getTrainingPartnerDetail" +e);
			return null;
		}
	}
}
