package com.nskfdc.scgj.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.InsertBatchNumberConfig;
import com.nskfdc.scgj.dto.InsertBatchNumberDto;

@Repository
public class InsertBatchNumberDao extends AbstractTransactionalDao {

	@Autowired
	private InsertBatchNumberConfig insertBatchNumberConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(InsertBatchNumberDao.class);
	private static final ShowDetailsRowMapper showDetailsRowMapper = new ShowDetailsRowMapper();

	public Integer checkBatchNumberExistence(String batchId, String scgjBatchNumber) {
		try {

			int existenceStatus = -1;
			LOGGER.debug("In method checkBatchNumberExistence to check the existence of batch number : " + scgjBatchNumber);			
			LOGGER.debug("Creating hashmap of object to insert scgjBatchNumber into hashmap");
			Map<String,Object> params = new HashMap<>();
			params.put("scgjBatchNumber",scgjBatchNumber);
			if(params.isEmpty())
			{
				LOGGER.debug("The parameters are empty");
				return -299;
			}
			existenceStatus = getJdbcTemplate().queryForObject(insertBatchNumberConfig.getCheckBatchNumberExistence(), params, Integer.class);
			LOGGER.debug("The value of user existence is : " + existenceStatus);

			if (existenceStatus == 0) {
				LOGGER.debug("SCGJ Batch Number" + scgjBatchNumber + " " + "does not exists");
				LOGGER.debug("Creating hashmap to insert scgj batch number for the batch Id " + batchId);
				
				Map<String, Object> insertParams = new HashMap<>();
				insertParams.put("scgjBatchNumber", scgjBatchNumber);
				insertParams.put("batchId", batchId);
				
				return getJdbcTemplate()
						.update(insertBatchNumberConfig.getUpdateBatchNumber(), insertParams);
				
			} else {
				LOGGER.debug("SCGJ Batch Number already exists");
				LOGGER.debug("Returning error code -299");
				return -299;
			}

		} catch (Exception e) {
			LOGGER.error("An error occured while checking the existence of batch number against the batch id :" + e);
			LOGGER.error("Returning -25 as the error code");
			return -239;
		}
	}

	/**
	 * This method updates the scgj batch Number against the given batch id
	 * 
	 * @param batchId
	 * @param scgjBatchNumber
	 * @return
	 */

	public Integer updateScgjBatchNumber(String batchId, String scgjBatchNumber) {
		// TODO Auto-generated method stub
		LOGGER.debug("Request recieved to insert batch number against the batch id : " + batchId);

		try {

			Integer checkExistenceStatus = -254;
			Integer updateStatus = -99;
			LOGGER.debug("Checking existence of batch number for the update functionality");
			LOGGER.debug("Creating hash map of objects");
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("scgjBatchNumber", scgjBatchNumber);
			checkExistenceStatus = getJdbcTemplate()
					.queryForObject(insertBatchNumberConfig.getCheckBatchNumberExistence(), parameters, Integer.class);
		
			if(checkExistenceStatus == 1)
			{
				LOGGER.debug("SCGJ Batch Number : " + scgjBatchNumber + " " + "Already exists");
				LOGGER.debug("Returning error code -699");
				return -699;
			}

			else
			{
				LOGGER.debug("SCGJ Batch Number does not exists... Updating the scgj batch number against the batch id : " + batchId);
			LOGGER.debug("Creating hashmap of objects to insert batch number against the batch id" + batchId);
			Map<String,Object> updateParams = new HashMap<>();
			updateParams.put("batchId", batchId);
			updateParams.put("scgjBatchNumber", scgjBatchNumber);
			if(updateParams.isEmpty())
			{
				LOGGER.debug("Parameters are empty");
				return -585;
			}

			updateStatus = getJdbcTemplate().update(insertBatchNumberConfig.getUpdateBatchNumber(), updateParams);
			LOGGER.debug("The update status for scgj batch number is : " + updateStatus);
			return updateStatus;
			}
		} 
		catch (Exception e) {
			LOGGER.error("An error occured while updating scgj batch number " + scgjBatchNumber + " " + e);
			LOGGER.debug("Returning error code: -585");
			return -585;
		}

	}

	
	public Collection<InsertBatchNumberDto> showBatchNumberDetails(String batchId)
	{
		LOGGER.debug("Request recieved from service to get details of scgj batch number against the batch id : " + batchId);
		LOGGER.debug("Checking if batch number exists for batch id" + batchId);
		Integer checkId = -58;
		try
		{
		LOGGER.debug("Creating hashmap to insert batch id");
		Map<String,Object> parameter = new HashMap<>();
		parameter.put("batchId", batchId);
			if(parameter.isEmpty())
			{
				LOGGER.error("Parameters in hashmap are empty returning null");
				return null;
			}
		
		checkId = getJdbcTemplate().queryForObject(insertBatchNumberConfig.getCheckBatchNumberForBatchId(), parameter, Integer.class);
	    LOGGER.debug("The value of checkId variable is : " + checkId);
	    
	    if(checkId!=null)
	    {
	    	LOGGER.debug("SCGJ batch number exists for batch id : " + batchId);
	    	LOGGER.debug("Creating hashmap to get details related to scgj batch number");
	    	Map<String,Object> details = new HashMap<>();
	    	details.put("batchId", batchId);
	    	
	    	if(details.isEmpty())
			{
				LOGGER.error("Parameters in hashmap are empty on line number 152 ... returning null");
				return null;
			}
	    	LOGGER.debug("Executing query to get details related to batch number");
	    	return getJdbcTemplate().query(insertBatchNumberConfig.getShowBatchNumberDetails(), details, showDetailsRowMapper);
	    	
	    }
	    else
	    {
	    	LOGGER.debug("No SCGJ Batch number present against the batch id : " + batchId);
	    	return null;
	    }
		
		}
		catch(Exception e)
		{
			LOGGER.error("An exception occured while retrieving the details about batch number : " + e);
			LOGGER.error("Returning NULL!");
			return null;
		}
		
	}
	
	
	private static class ShowDetailsRowMapper implements RowMapper<InsertBatchNumberDto>
	{
		@Override
		public InsertBatchNumberDto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String batchId = rs.getString("batchId");
			String scgjBatchNumber = rs.getString("SDMSBatchNumber");
			Date updatedOn = rs.getDate("updatedOn");
			String trainingPartnerName = rs.getString("trainingPartnerName");
			
			return new InsertBatchNumberDto(batchId,scgjBatchNumber,updatedOn,trainingPartnerName);
			
		}
	}
	
}