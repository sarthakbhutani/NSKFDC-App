package com.nskfdc.scgj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DataImportConfig;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.PanelDataImportDto;

@Repository
public class DataImportDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportDao.class);
	
	@Autowired
	private DataImportConfig dataImportConfig;
	
	public void importMasterSheet() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}
	
	public void getImportHistory() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}
	
	public void getSearchedMasterSheet() {
		//write Logger here
		
				try {
					
					//write Logger here 
					//write code here and change the return type
				}catch(Exception e) {
					
					// write Logger
					// return null;
				}
				
				
			}

	
	public Integer generateBatchDao(String trainingPartnerEmail){
					
			LOGGER.debug("Request received from Service");
					
			LOGGER.debug("In  Import Dao, to get create batch for email id:" + trainingPartnerEmail);
					
			Map<String, Object> parameters = new HashMap<>();
					
			parameters.put("trainingPartnerEmail",trainingPartnerEmail);
					
			if(parameters.isEmpty())
			{
				LOGGER.error("Null Parameter");
			}
			
			try{
			        
				Integer result = getJdbcTemplate().update(dataImportConfig.getGenerateBatch(),parameters);
						
				LOGGER.debug("The result of the query is : " + result);
						
				return result;
			}
					
			catch(DataAccessException d) {
						
				LOGGER.error("DataAccessException in Dao" + d);
						
				return -1;
			}
					
			catch(Exception e) {
						
				LOGGER.error("In Catch Block");
						
				LOGGER.error("An error occured while generating the batch" + e);
						
				return -1;
			}
	}
			
			//write Rowmapper class here
	private static final BATCHRowmapper BATCH_RowMapper = new BATCHRowmapper();
	
	
	public Collection<BatchDto> getBatchDetail(){
		
	
		
		
		try {
			
		
			return getJdbcTemplate().query(dataImportConfig.getshowbatchId(), BATCH_RowMapper);
			
		} catch (Exception e) {
			
			
			return null;
			
		}
		
	}
	
	private static class BATCHRowmapper implements RowMapper<BatchDto>{
		
		@Override
		public BatchDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String batchId = rs.getString("batchId");
			
			return new BatchDto(batchId);
			
		}
	}
	
	
	
	
	
public int getTotalTargets(){
		
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In Total Targets Dao, to get Total Targets");
		
		
		try {
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get TotalTargets");
			Map<String,Object> parameters = new HashMap<> ();
			return getJdbcTemplate().queryForObject(dataImportConfig.getShowTotalTargets(),parameters,Integer.class);
			
		} 
		catch(DataAccessException de)
		{
			LOGGER.error("A data access exception has occured: " + de);
			LOGGER.error("Returning zero");
			return 0;
		}
		
		catch(Exception e)
		{
			LOGGER.error("An Exception occured: " + e);
			return 0;
		}
		
	}

public int getTargetAchieved(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Target Achieved Dao, to get Target Achieved");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Target Achieved");
		Map<String,Object> parameters = new HashMap<> ();
		return getJdbcTemplate().queryForObject(dataImportConfig.getShowTargetAchieved(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning zero");
		return 0;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An Exception occured: " + e);
		return 0;
	}
	
}
public int getRemainingTargets(){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Remaining Target Dao, to get Remaining Target");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Target Achieved");
		Map<String,Object> parameters = new HashMap<> ();
		return getJdbcTemplate().queryForObject(dataImportConfig.getShowRemainingTargets(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning zero");
		return 0;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An Exception occured: " + e);
		return 0;
	}
	
}



}
