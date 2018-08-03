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
import org.springframework.dao.DataAccessException;
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
		
		
        try {
        	LOGGER.debug("TRYING -- userExistence - to check existence of entered userEmail");
        	LOGGER.debug("Creating HashMap object");
        	Map<String,Object> parameters = new HashMap<>();
        	LOGGER.debug("Inserting userEmail in parameters");
        	parameters.put("userEmail", userEmail);
        	LOGGER.debug("Executing query to check if userEmail exists");
        	int result = getJdbcTemplate().queryForObject(loginConfigSql.getCheckUserSql(), parameters ,Integer.class);
        	LOGGER.debug("Returning existence result");
        	return result; 
        }
        catch(Exception e) {
        	LOGGER.error("CATCHING -- Exception Handled in LoginDao");
        	LOGGER.error("In method - userExistence, while checking userEmail existence ");
        	LOGGER.error("Exception is"+e);
        	LOGGER.error("Returning -1");
        	return -1;
        }
		
	}
	



	public String getNameOfUser(String userEmail) {
		LOGGER.debug("Request Received from GetNameOfUserService to get name of the logged in user");
		LOGGER.debug("In LoginDao - getNameOfUser");
		LOGGER.debug("Creating HashMap object");
		Map<String, Object> parameters = new HashMap<>();
	
		
		LOGGER.debug("Inserting logged in userEmail in parameters");
		parameters.put("userEmail", userEmail);
		try {
			
			LOGGER.debug("TRYING -- To get logged in User Detail");
			LOGGER.debug("Executing query to get name of the logged in user");
			return getJdbcTemplate().queryForObject(loginConfigSql.getGetNameOfUser(), parameters, String.class);
			
		} catch (Exception e) {
			
			LOGGER.error("CATCHING -- Exception Handled in LoginDao");
        	LOGGER.error("In method - getNameOfUser, while getting name of Logged in User ");
        	LOGGER.error("Exception is"+e);
        	LOGGER.error("Returning null");
			return e.getLocalizedMessage();
		}
	}

	
   public SessionManagementDto getValidUserDetails(String userEmail) {
		LOGGER.debug("Request Received from LoginService");
		LOGGER.debug("In LoginDao - getValidateLoginUser");
		LOGGER.debug("To get login details of user to Validate");
		LOGGER.debug("Creating HashMap object");
		Map<String, Object> parameters = new HashMap<>();
		
		LOGGER.debug("Inserting userEmail to HashMap object");
		parameters.put("userEmail", userEmail);
				
        try {
        	
        	LOGGER.debug("TRYING -- To get login details of user in Login Dao");	
        	LOGGER.debug("Executing SQL query and returning response");        	
        	return getJdbcTemplate().queryForObject(loginConfigSql.getGetValidUserDetails(),parameters,login_RowMapper);        	
        }catch(Exception e) {
        	
        	LOGGER.error("CATCHING -- Exception Handled in LoginDao");
        	LOGGER.error("In method - getValidUserDetails, while getting Login details for entered userEmail ");
        	LOGGER.error("Exception is"+e);
        	LOGGER.error("Returning null");
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
