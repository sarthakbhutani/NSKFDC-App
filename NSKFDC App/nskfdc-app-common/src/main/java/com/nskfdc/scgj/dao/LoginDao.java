package com.nskfdc.scgj.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.LoginConfigSql;
import com.nskfdc.scgj.dto.SessionManagementDto;

@Repository
public class LoginDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);
	
	private static LoginRowMapper loginRowMapper = new LoginRowMapper();
	
	
	@Autowired
	private LoginConfigSql loginConfigSql;
	
	
	public Integer userExistence(String username){
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In LoginDao - userExistence");
		 	
		LOGGER.debug("Checking Existense of User");
		
		LOGGER.debug("Creating HashMap object");
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("object created successfully");
		
		LOGGER.debug("Inserting parameters to HashMap object");
    	parameters.put("username", username);
    	
		LOGGER.debug("Parameters inserted");
		
		LOGGER.debug("Executing SQL query and returning response");
		
        try {
        	
        	LOGGER.debug("In try block for user existence in Login Dao");
        	
        	
        	return getJdbcTemplate().queryForObject(loginConfigSql.getCheckUserSql(), parameters, Integer.class);
        	
        }catch(Exception e) {
        	
        	LOGGER.debug("Error occured while checking user existence for Login" + e);
        	return null;
        }
		
	}
	
	public SessionManagementDto getValidUserDetails(String username) {
		LOGGER.debug("Request Received from Service");
		LOGGER.debug("In LoginDao - getValidateLoginUser");
		
		LOGGER.debug("Creating HashMap object");
		Map<String, Object> parameters = new HashMap<>();
		LOGGER.debug("object created successfully");
		
		LOGGER.debug("Inserting parameters to HashMap object");
		parameters.put("username", username);
		
		LOGGER.debug("Parameters inserted");
		
		LOGGER.debug("Executing SQL query and returning response");
        try {
        	
        	LOGGER.debug("In try block to get name of user in Login Dao");	
        	
        	return getJdbcTemplate().query(loginConfigSql.getGetUserName(), parameters, loginRowMapper).iterator().next();
        	
        	
        	
        }catch(Exception e) {
        	
        	LOGGER.debug("Error occured while getting valid user details on login" + e);
        	return null;
        	
        }
	}
	
	private static class LoginRowMapper implements RowMapper<SessionManagementDto>{
		
		@Override
		public SessionManagementDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String username = rs.getString("username");
			String password = rs.getString("password");
			String userrole = rs.getString("userrole");
				
			return new SessionManagementDto(username, password, userrole);
			
		}
		
	}
	

}
