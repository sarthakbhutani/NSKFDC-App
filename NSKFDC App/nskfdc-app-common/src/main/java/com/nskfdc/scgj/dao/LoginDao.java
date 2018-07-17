package com.nskfdc.scgj.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.LoginConfigSql;

import com.nskfdc.scgj.dto.LoginDto;
import com.nskfdc.scgj.dto.SessionManagementDto;

@Repository
public class LoginDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);
		
	private static final LoginRowMapper login_RowMapper = new LoginRowMapper();
	
	@Autowired
	private LoginConfigSql loginConfigSql;
	
	
	public int userExistence(String userEmail){
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In LoginDao - userExistence");

		LOGGER.debug("Checking Existense of User");
		LOGGER.debug("Creating HashMap object");
		
		
		LOGGER.debug("object created successfully");
		LOGGER.debug("Inserting parameters to HashMap object");
		
    	
    	
		LOGGER.debug("Parameters inserted");
		LOGGER.debug("Executing SQL query and returning response");
		
        try {
        	Map<String,Object> parameters = new HashMap<>();
        	parameters.put("userEmail", userEmail);
        	LOGGER.debug("In try block for user existence in Login Dao" + parameters);
        	int result = getJdbcTemplate().queryForObject(loginConfigSql.getCheckUserSql(), parameters ,Integer.class);
        	LOGGER.debug("Executed details are" + result);
        	return result; 
        }
        catch(Exception e) {
        	
        	LOGGER.debug("Error occured while checking user existence for Login" + e);
        	return -1;
        }
		
	}
	




	
   public SessionManagementDto getValidUserDetails(String userEmail) {
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In LoginDao - getValidateLoginUser");
		
		LOGGER.debug("Creating HashMap object");
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("object created successfully");
		
		LOGGER.debug("Inserting parameters to HashMap object");
		parameters.put("userEmail", userEmail);
		
		LOGGER.debug("Parameters inserted"+parameters);
		
		LOGGER.debug("Executing SQL query and returning response");
        try {
        	
        	LOGGER.debug("In try block to get details of user in Login Dao");	
        	
        	
        	return getJdbcTemplate().queryForObject(loginConfigSql.getGetValidUserDetails(),parameters,login_RowMapper);
        	
        	
        }catch(Exception e) {
        	
        	LOGGER.debug("Error occured while getting valid user details on login" + e);
        	return null;
        	
        }
	}
	
	
	
   
	private static class LoginRowMapper implements RowMapper<SessionManagementDto>
	{
		public SessionManagementDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String userEmail = rs.getString("userEmail");
			String password = rs.getString("password");
			String role = rs.getString("role");
			
			return new SessionManagementDto(userEmail, password, role);
		}	
  
		
	}
   

}
