package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.GenerateCredentialConfig;


import com.nskfdc.scgj.dto.GenerateCredentialDto;
import com.nskfdc.scgj.dto.GenerateCredentialSearchDto;

@Repository
public class GenerateCredentialDao  extends AbstractTransactionalDao {
		
		private static final Logger LOGGER= LoggerFactory.getLogger(GenerateCredentialDao.class);
		int result;
		private static final GenerateCredentialSearchRowmapper GenerateCredentialSearch_RowMapper = new GenerateCredentialSearchRowmapper();
		//private static final GenerateCredentialRowmapper GenerateCredential_RowMapper = new GenerateCredentialRowmapper();
		@Autowired
		private GenerateCredentialConfig generateCredentialConfig;
		
		public Integer generateCredential(GenerateCredentialDto generateCredentialDto , String role)
		{
		    LOGGER.debug("Received request from service to generate credentials for trainingpartner with email: " + generateCredentialDto.getUserEmail());
		    
		    try
		    {
		    	LOGGER.debug("Creating hashmap of object to generate credential of user with email : " + generateCredentialDto.getUserEmail());
		    	Map<String,Object> parameter = new HashMap<>();
		    	LOGGER.debug("Hashmap of object created to generate credentials");
		    	parameter.put("userEmail", generateCredentialDto.getUserEmail());
		    	parameter.put("password", generateCredentialDto.getPassword());
		    	parameter.put("role", role);
		    	LOGGER.debug("Parameters inserted into hashmap : "  + parameter.get("userEmail"));
		    	LOGGER.debug("Parameters inserted into hashmap : "  + parameter.get("password"));
		    	LOGGER.debug("Parameters inserted into hashmap : "  + parameter.get("role"));
		    	return getJdbcTemplate().update(generateCredentialConfig.getUsertableCredential(), parameter);
		    }
		    
		    catch(DataAccessException dE)
		    {
		    	LOGGER.error("An exception has occured while generating credentials for training partner : " + dE);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    }
		    catch(Exception e)
		    {
		    	LOGGER.error("An exception has occured while generating credentials for training partner : " + e);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    	
		    }
			
		    
		}
		
		public Integer inputTrainingPartnerDetails(GenerateCredentialDto generateCredentialDto)
		{
			LOGGER.debug("In method inputTrainingPartnerDetails to enter the details of training partner with email: " + generateCredentialDto.getUserEmail());
		    try
		    {
			
			   LOGGER.debug("Creating hashmap of object to enter training partner details");
		       Map<String,Object> trainingPartnerDetail = new HashMap<>();
		       trainingPartnerDetail.put("nsdcRegNumber", generateCredentialDto.getNsdcRegNumber());
		       trainingPartnerDetail.put("trainingPartnerName", generateCredentialDto.getTrainingPartnerName());
		       trainingPartnerDetail.put("userEmail", generateCredentialDto.getUserEmail());
		       trainingPartnerDetail.put("jobRole", generateCredentialDto.getJobRole());
		       trainingPartnerDetail.put("sectorSkillCouncil", generateCredentialDto.getSectorSkillCouncil());
		       trainingPartnerDetail.put("targets", generateCredentialDto.getTargets());
	    		
	    	return getJdbcTemplate().update(generateCredentialConfig.getTrainingPartnerCredentials(), trainingPartnerDetail);
		    }
		   
		    
		    catch(DataAccessException dE)
		    {
		    	LOGGER.error("An exception has occured while generating credentials for training partner : " + dE);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    }
		    catch(Exception e)
		    {
		    	LOGGER.error("An exception has occured while generating credentials for training partner : " + e);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    	
		    }
        }
		
		
	
		
		public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(String nsdcRegNumber){
			
			LOGGER.debug("Request received from Service");
			LOGGER.debug("In Generate credential Dao, to get Training Partner Detail ");
			
			
			try {
				
				LOGGER.debug("In try block");
				LOGGER.debug("Execute query to get training partner details ");
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("nsdcRegNumber",nsdcRegNumber);
				return getJdbcTemplate().query(generateCredentialConfig.getShowTrainingPartnerDetails(),parameters,GenerateCredentialSearch_RowMapper);
				
			} catch (Exception e) {
				
				LOGGER.debug("In Catch Block");
				LOGGER.debug("An error occured while getting the training partner details " + e);
				return null;
				
			}
			
		}
		
		private static class GenerateCredentialSearchRowmapper implements RowMapper<GenerateCredentialSearchDto>{
			
			@Override
			public GenerateCredentialSearchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				String nsdcRegNumber= rs.getString("nsdcRegNumber");
				String trainingPartner = rs.getString("trainingPartnerName");
				String userEmail = rs.getString("userEmail");
				String password = rs.getString("password");
				Date generatedOn = rs.getDate("generatedOn");		
				
				return new GenerateCredentialSearchDto(nsdcRegNumber,trainingPartner,userEmail,password,generatedOn);
				
			}
			
			
		}

		public Integer checkUserExistence(String userEmail) {
		
			try
			{
				LOGGER.debug("Received request in DAO to check user existence for userEmail : " + userEmail);
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("userEmail", userEmail);
				LOGGER.debug("Parameters inserted into database");
				
				Integer test =  getJdbcTemplate().queryForObject(generateCredentialConfig.getCheckUserExistence(), parameters, Integer.class);
				LOGGER.debug("The value of check user existence status is : " + test);
				return test;
			}
			catch(Exception e)
			{
				LOGGER.error("An error occured while checking for user existence : " + e);
				
				return null;
			}
		}
}
