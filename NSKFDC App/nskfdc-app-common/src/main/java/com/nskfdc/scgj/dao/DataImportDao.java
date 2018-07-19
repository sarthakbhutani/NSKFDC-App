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
import com.nskfdc.scgj.dto.FinancialDto;
import com.nskfdc.scgj.dto.DownloadFinalMasterSheetDto;


@Repository
public class DataImportDao extends AbstractTransactionalDao{
	
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportDao.class);
	
	/*  Object of Master Sheet RowMapper */
	private static final DownloadMasterSheetRowmapper downloadMasterSheetRowMapper = new DownloadMasterSheetRowmapper();
	
	
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
	
	


	/*--------------------------Generate BatchId------------------------------------*/
	public Integer generateBatchDao(String userEmail){
					
			LOGGER.debug("Request received from Service");
					
			LOGGER.debug("In  Import Dao, to get create batch for email id:" + userEmail);
					
			Map<String, Object> parameters = new HashMap<>();
					
			parameters.put("userEmail",userEmail);
					
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
			
	/*--------------------------Download Final Master Sheet------------------------------------*/
	
	public Collection<DownloadFinalMasterSheetDto> downloadMasterSheetDao(String userEmail){
				
				LOGGER.debug("Request received from service");
				LOGGER.debug("In Generate Occupation Certificate Dao");
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("userEmail", userEmail);
				
				try {
					
					LOGGER.debug("In try block to download Final Master Sheet");
					
					return getJdbcTemplate().query(dataImportConfig.getDownloadMasterSheet(), parameters, downloadMasterSheetRowMapper);
				}
				
				catch(Exception e) {
							
					LOGGER.error("In Catch Block");			
					LOGGER.error("An error occured while downloading the Final Master Sheet" + e);
							
					return null;
			   }
	}
	
	
	
	
	
	
	private static final BATCHRowmapper BATCH_RowMapper = new BATCHRowmapper();
	
	
	public Collection<BatchDto> getBatchDetail(String userEmail){
	
		try {
			
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("userEmail", userEmail);
		
			return getJdbcTemplate().query(dataImportConfig.getshowbatchId(),parameters, BATCH_RowMapper);
			
		} catch (Exception e) {
			
			LOGGER.debug("An Exception occured while fetching batch id for email " + userEmail + e);
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
	
	
	
	//Author: Sagun Saluja
	
public Integer getTotalTargets(String userEmail){
		
		LOGGER.debug("Request received from Service");
		LOGGER.debug("In Total Targets Dao, to get Total Targets");
		
		
		try {
			
			LOGGER.debug("In try block");
			LOGGER.debug("Execute query to get TotalTargets");
			Map<String,Object> parameters = new HashMap<> ();
			parameters.put("userEmail", userEmail);
			return getJdbcTemplate().queryForObject(dataImportConfig.getShowTotalTargets(),parameters,Integer.class);
			
		} 
		catch(DataAccessException de)
		{
			LOGGER.error("A data access exception has occured: " + de);
			LOGGER.error("Returning zero");
			return null;
		}
		
		catch(Exception e)
		{
			LOGGER.error("An Exception occured: " + e);
			return null;
		}
		
	}

public Integer getTargetAchieved(String userEmail){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Target Achieved Dao, to get Target Achieved");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Target Achieved");
		Map<String,Object> parameters = new HashMap<> ();
		parameters.put("userEmail", userEmail);
		return getJdbcTemplate().queryForObject(dataImportConfig.getShowTargetAchieved(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning zero");
		return null;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An Exception occured: " + e);
		return null;
	}
	
}
public Integer getRemainingTargets(String userEmail){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Remaining Target Dao, to get Remaining Target");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get Target Achieved");
		Map<String,Object> parameters = new HashMap<> ();
		parameters.put("userEmail", userEmail);
		return getJdbcTemplate().queryForObject(dataImportConfig.getShowRemainingTargets(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning zero");
		return null;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An Exception occured: " + e);
		return null;
	}
	
}
public Integer ShowFinancialYear(String userEmail){
	
	LOGGER.debug("Request received from Service");
	LOGGER.debug("In Remaining Target Dao, to get FinancialYear");
	
	
	try {
		
		LOGGER.debug("In try block");
		LOGGER.debug("Execute query to get FinancialYear");
		Map<String,Object> parameters = new HashMap<> ();
		parameters.put("userEmail", userEmail);
		return getJdbcTemplate().queryForObject(dataImportConfig.getShowFinancialYear(),parameters,Integer.class);
		
	} 
	catch(DataAccessException de)
	{
		LOGGER.error("A data access exception has occured: " + de);
		LOGGER.error("Returning zero");
		return null;
	}
	
	catch(Exception e)
	{
		LOGGER.error("An Exception occured: " + e);
		return null;
	}
	
}
private static final FinancialRowmapper Financial_RowMapper = new FinancialRowmapper();


public Collection<FinancialDto> FinancialRowmapper(){
	try {
		
	
		return getJdbcTemplate().query(dataImportConfig.getShowFinancialYear(), Financial_RowMapper);
		
	} catch (Exception e) {
		
		
		return null;
		
	}
	
}

private static class FinancialRowmapper implements RowMapper<FinancialDto>{
	
	@Override
	public FinancialDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String financialYear = rs.getString("FinancialDto");
		
		return new FinancialDto(financialYear);
		
	}
}

/*------------------------------RowMapper to download Master Sheet-----------------------------*/
		private static class DownloadMasterSheetRowmapper implements RowMapper<DownloadFinalMasterSheetDto>{
		
			@Override
			public DownloadFinalMasterSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							
				String trainingPartnerName = rs.getString("trainingPartnerName");
				String sectorSkillCouncil = rs.getString("sectorSkillCouncil");
				String jobRole = rs.getString("jobRole");
				String nsdcRegNumber = rs.getString("nsdcRegNumber");
				String batchId = rs.getString("batchId");
				
				return new DownloadFinalMasterSheetDto(trainingPartnerName,sectorSkillCouncil,jobRole,nsdcRegNumber,batchId);	
			}	
		}


}






