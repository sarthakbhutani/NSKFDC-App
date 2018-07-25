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
		LOGGER.debug("Request received from controller to generate credentials");
		Integer userExistenceStatus = -25;
		LOGGER.debug("Checking user credentials for training partner with email : " + generateCredentialDto.getUserEmail());
	
	  Integer  userExistence = generatecredentialDao.checkUserExistence(generateCredentialDto.getUserEmail());
	  Integer  nsdcRegNumberExistence = generatecredentialDao.checkNsdcNumberExistence(generateCredentialDto.getNsdcRegNumber());
		
	    if(userExistence == 0 && nsdcRegNumberExistence == 0)
	    {
	    	
	    	String role = "TP";
	    	LOGGER.debug("User existence status for training partner with email : " + generateCredentialDto.getUserEmail() + "is : "  + userExistence);
	    	return getGenerateCredentialService(generateCredentialDto,role);
	    
	    
	    	
	    
	    	}
	    
	    else
	    {
	    	LOGGER.debug("User existence status for training partner with email : " + generateCredentialDto.getUserEmail() + "is : "  + userExistence);
	    	LOGGER.debug("The value of user existence is : " + userExistenceStatus);
	    	return userExistenceStatus;
	    }
	    
	    
	}
	
	
	
	public Integer getGenerateCredentialService(GenerateCredentialDto generateCredentialDto,String role)
	{

		LOGGER.debug("Received paramters to be inserted into database from controller");
		
		Integer userCredentialStatus = - 10;
		
		userCredentialStatus = generatecredentialDao.generateCredential(generateCredentialDto, role);
		
		LOGGER.debug("The status of generation of credentials of user with email : " + generateCredentialDto.getUserEmail() + " is : " + userCredentialStatus);
		
		if(userCredentialStatus == 1)
		{
			LOGGER.debug("Credentials generated for training partner with email : " + generateCredentialDto.getUserEmail());

			return generatecredentialDao.inputTrainingPartnerDetails(generateCredentialDto);	
		}
		
		else
		{
			return userCredentialStatus;
		}

	
	}

	public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(String nsdcRegNumber){
		
		LOGGER.debug("Request received from Control");
		LOGGER.debug("In  training Partner Service, to get training Partner details ");
		
		try {
			LOGGER.debug("In try block to get training partner details");
			return generatecredentialDao.getTrainingPartnerDetail(nsdcRegNumber);
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the training partner details"+ e);
			return null;
		}
	}
}
