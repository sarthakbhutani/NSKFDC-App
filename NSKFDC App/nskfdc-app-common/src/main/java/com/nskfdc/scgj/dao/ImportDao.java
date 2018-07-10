package com.nskfdc.scgj.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.nskfdc.scgj.common.AbstractTransactionalDao;
import com.nskfdc.scgj.config.DataImportConfig;

@Repository
public class ImportDao extends AbstractTransactionalDao{
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ImportDao.class);
	
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
			        
				Integer result = getJdbcTemplate().update(dataImportConfig.getImportBatchDetails(),parameters);
						
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
	

}
