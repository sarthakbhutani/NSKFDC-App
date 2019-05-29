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
		
		@Autowired
		private GenerateCredentialConfig generateCredentialConfig;
		
		/**
		 * Method to generate credentials of a training partner 
		 * @param generateCredentialDto
		 * @param role
		 * @return status of the process
		 */
		public Integer generateCredential(GenerateCredentialDto generateCredentialDto , String role)
		{
		    LOGGER.debug("Received request from service to generate new credentials for trainingpartner");
		    LOGGER.debug("In GenerateCredentialDao - generateCredential");
		    
		    try
		    {
		    	LOGGER.debug("TRYING -- to insert the credential in User Table");
		    	LOGGER.debug("Creating hashmap of object to generate credential of user with email");
		    	Map<String,Object> parameter = new HashMap<>();
		    	LOGGER.debug("Inserting email, password & role in parameter");
		    	parameter.put("userEmail", generateCredentialDto.getUserEmail());
		    	parameter.put("password", generateCredentialDto.getPassword());
		    	parameter.put("role", role);
		    	LOGGER.debug("Execute query to insert new credentials in user table");
		    	return getJdbcTemplate().update(generateCredentialConfig.getUsertableCredential(), parameter);
		    }
		    
		    catch(DataAccessException dE)
		    {
		    	LOGGER.error("CATCHING -- DataAccessException handled while inserting new credentials");
		    	LOGGER.error("In GenerateCredentialDao - generateCredential"+ dE);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    }
		    catch(Exception e)
		    {
		    	LOGGER.error("CATCHING -- Exception handled while inserting new credentials");
				LOGGER.error("In GenerateCredentialDao - generateCredential"+e);
				LOGGER.error("Returning -10");
		    	return -10;
		    	
		    }
			
		    
		}
		
		/**
		 * Method to get details of training partner
		 * @param generateCredentialDto
		 * @return status of process
		 */
		public Integer inputTrainingPartnerDetails(GenerateCredentialDto generateCredentialDto)
		{
			 	LOGGER.debug("Received request from service to insert training partner data while generating credential");
			    LOGGER.debug("In GenerateCredentialDao - inputTrainingPartnerDetails");
			    
		    try
		    {
		    	LOGGER.debug("TRYING -- to insert the credential in trainingPartnerDetails Table");
		    	LOGGER.debug("Creating hashmap of object to insert training partner data");
		       Map<String,Object> trainingPartnerDetail = new HashMap<>();
		       LOGGER.debug("Inserting nsdcRegNumber, trainingPartnerName, userEmail, jobRole, sectorSkillCouncil, targets in parameter");
		       trainingPartnerDetail.put("nsdcRegNumber", generateCredentialDto.getNsdcRegNumber());
		       trainingPartnerDetail.put("trainingPartnerName", generateCredentialDto.getTrainingPartnerName());
		       trainingPartnerDetail.put("userEmail", generateCredentialDto.getUserEmail());
		       trainingPartnerDetail.put("jobRole", generateCredentialDto.getJobRole());
		       trainingPartnerDetail.put("sectorSkillCouncil", generateCredentialDto.getSectorSkillCouncil());
		       trainingPartnerDetail.put("targets", generateCredentialDto.getTargets());
		       LOGGER.debug("Execute query to insert training partner details while generating credential");
		       return getJdbcTemplate().update(generateCredentialConfig.getTrainingPartnerCredentials(), trainingPartnerDetail);
		    }
		   
		    
		    catch(DataAccessException dE)
		    {
		    	LOGGER.error("CATCHING -- DataAccessException handled while inserting training partner details");
		    	LOGGER.error("In GenerateCredentialDao - inputTrainingPartnerDetails"+ dE);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    }
		    catch(Exception e)
		    {
		    	LOGGER.error("CATCHING -- Exception handled while inserting training partner details");
		    	LOGGER.error("In GenerateCredentialDao - inputTrainingPartnerDetails"+ e);
		    	LOGGER.error("Returning -10");
		    	return -10;
		    	
		    }
        }
		
		
	
		/**
		 * Method to send data when search using NSDC registration number
		 * @param nsdcRegNumber
		 * @return result of the seach sql
		 */
		public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(String nsdcRegNumber){
			
			LOGGER.debug("Received request from service to get credential detail");
			LOGGER.debug("Corresponding to searched NSDC Reg Number");
		    LOGGER.debug("In GenerateCredentialDao - getTrainingPartnerDetail");
		    
	    try
	    {
		    	LOGGER.debug("TRYING -- To get training partner credential details");
		    
				Map<String, Object> parameters = new HashMap<>();
				LOGGER.debug("Inserting nsdc Reg number in parameters map");
				parameters.put("nsdcRegNumber",nsdcRegNumber);
				LOGGER.debug("Executing query to get Training partner credential details for searched NSDC Reg Number");
				return getJdbcTemplate().query(generateCredentialConfig.getShowTrainingPartnerDetails(),parameters,GenerateCredentialSearch_RowMapper);
				
			} catch (Exception e) {
				
				LOGGER.error("CATCHING -- Exception handled while gettin training partner credential details");
		    	LOGGER.error("In GenerateCredentialDao - getTrainingPartnerDetail"+ e);
		    	LOGGER.error("Returning null");
				return null;
				
			}
			
		}
		
		
		/**
		 * Row mapper class for training partners details
		 * @author Ruchi
		 *
		 */
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

		/**
		 * Check for user existence before creating credentials
		 * @param userEmail email to be searched
		 * @return result of the search performed
		 */
		public Integer checkUserExistence(String userEmail) {
			
			LOGGER.debug("Received request in GenerateCredentialDao");
			LOGGER.debug(" To Check existence of entered email id");
			
			try
			{
				LOGGER.debug("TRYING -- checkUserExistence");
				Map<String, Object> parameters = new HashMap<>();
				LOGGER.debug("Inserting email id in parameters");
				parameters.put("userEmail", userEmail);
				LOGGER.debug("Executing query for checking email existence");
				Integer userCredentialGenerationStatus =  getJdbcTemplate().queryForObject(generateCredentialConfig.getCheckUserExistence(), parameters, Integer.class);
				LOGGER.debug("Returning the existence status code");
				return userCredentialGenerationStatus;
			}
			catch(Exception e)
			{
				LOGGER.error("CATCHING -- Exception handled while checking Email Existence");
				LOGGER.error("In GenerateCredentialDao - checkUserExistence"+e);
				LOGGER.error("Returning null");
				return null;
			}
		}
		
		/**
		 * To validate if nsdc registration number is not being used
		 * @param nsdcRegNumber 
		 * @return value of search 
		 */
		public Integer checkNsdcNumberExistence(String nsdcRegNumber) {
			
			LOGGER.debug("Received request in GenerateCredentialDao");
			LOGGER.debug(" To Check existence of entered NSDC Reg Number");
			
			
			try
			{
				LOGGER.debug("TRYING -- checkNsdcNumberExistence");
				Map<String, Object> parameters = new HashMap<>();
				LOGGER.debug("Inserting NSDC Reg Number in parameters");
				parameters.put("nsdcRegNumber", nsdcRegNumber);
				LOGGER.debug("Executing query for checking nsdc Reg Number existence");				
				Integer userCredentialGenerationStatus =  getJdbcTemplate().queryForObject(generateCredentialConfig.getCheckNsdcNumberExistence(), parameters, Integer.class);
				LOGGER.debug("Returning the NSDC Reg Number existence status code");
				return userCredentialGenerationStatus;
			}
			catch(Exception e)
			{
				LOGGER.error("CATCHING -- Exception handled while checking NSDC Reg Number Existence");
				LOGGER.error("In GenerateCredentialDao - checkNsdcNumberExistence"+e);
				LOGGER.error("Returning null");
				return null;
			}
		}
}
