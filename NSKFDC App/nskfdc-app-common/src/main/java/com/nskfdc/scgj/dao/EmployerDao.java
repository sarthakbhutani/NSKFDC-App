package com.nskfdc.scgj.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.EmployerConfigSql;

@Repository
public class EmployerDao extends AbstractTransactionalDao{

	@Autowired
	private EmployerConfigSql employerConfigSql;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployerDao.class);
	
	/**
	 * Method to insert details of Employer
	 * @param employerName
	 * @param employerContactNumber
	 * @param batchId
	 * @param userEmail
	 * @return
	 */
	public int insertEmployer(String employerName, String employerContactNumber, String batchId, String userEmail)
	{ 
		int status = -10;
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("employerName", employerName);
		parameters.put("employerContactNumber", employerContactNumber);
		parameters.put("batchId", batchId);
		parameters.put("userEmail", userEmail);
		try
		{
			LOGGER.debug("TRYING -- To insert Employer details");
			LOGGER.debug("In EmployerDao - insertEmployer");
			LOGGER.debug("Executing query to insert the employer details");
			status = getJdbcTemplate().update(employerConfigSql.getInsert(),parameters);
		}
		catch(Exception e)
		{
			
			LOGGER.error("CATCHING -- exception occured while inserting employer details" + e);
			LOGGER.error("In EmployerDao - insertEmployer");
			LOGGER.error("Returning -2");
			status = -2;
		}
		
	return status;
	}
	
	/**
	 * Method to update Employer details
	 * @param employerName
	 * @param employerContactNumber
	 * @param batchId
	 * @param userEmail
	 * @return
	 */
	public int updateEmployer(String employerName, String employerContactNumber, String batchId, String userEmail)
	{
		int status = -10;
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("employerName", employerName);
		parameters.put("employerContactNumber", employerContactNumber);
		parameters.put("batchId", batchId);
		parameters.put("userEmail", userEmail);
		try
		{
			LOGGER.debug("TRYING -- To update Employer details");
			LOGGER.debug("In EmployerDao - updateEmployer");
			LOGGER.debug("Executing query to update the employer details");
			status = getJdbcTemplate().update(employerConfigSql.getUpdate(), parameters);
		}
		catch(Exception e)
		{
			LOGGER.error("CATCHING -- exception occured while updating employer details" + e);
			LOGGER.error("In EmployerDao - updateEmployer");
			LOGGER.error("Returning -2");
			status =-2;
			
		}
		return status;
	}
	
	/**
	 * Method to check existence of employer based on batch Id and user email
	 * @param batchId
	 * @param userEmail
	 * @return
	 */
	public int employerExists(String batchId, String userEmail)
	{
		int existence =-10;
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("batchId", batchId);
		parameters.put("userEmail", userEmail);
		existence = getJdbcTemplate().queryForObject(employerConfigSql.getExists(),parameters,Integer.class);
		return existence;
	}
}
