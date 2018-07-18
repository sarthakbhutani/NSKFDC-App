package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.DataImportDao;
import com.nskfdc.scgj.dto.BatchDto;


@Service
public class DataImportService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportService.class);
	
	@Autowired
	private DataImportDao importHistoryDao;
	
	
/*--------------Method to Import Master Sheet----------------*/
	
	public void importMasterSheet() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.importMasterSheet();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}
	
	
	
	
/*--------------Method to get Import History----------------*/
	
	public void getImportHistory() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.getImportHistory();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}
	
	
	
	
/*--------------Method to get searched Master sheet----------------*/
	
	public void getSearchedMasterSheet() {
		
	//write LOGGER here
		
		try {
			//write LOGGER here		
			//write your logic here & change return type
			importHistoryDao.getSearchedMasterSheet();
			
		} catch (Exception e) {
			
			//write LOGGER here
			//return the default value, it can be null
		}
	}


/*---------------Method to create Batch----------------------------*/

	
	public int getGenerateBatchService(String trainingPartnerEmail){
		
		LOGGER.debug("Request received from Controller to create batch for email id: " + trainingPartnerEmail);
		
		LOGGER.debug("In Import Service to create batch for email id: " + trainingPartnerEmail );
		
		try{
			
			LOGGER.debug("In try block to generate batch for training partner with email id: " + trainingPartnerEmail);
			
			return importHistoryDao.generateBatchDao(trainingPartnerEmail);
		
		}
		
		catch(DataAccessException d) {
		
			LOGGER.error("DataAccessException in service to create Batch" + d);
			
			return -1;
		}
		
		catch(Exception e) {
			
			LOGGER.error("An error occurred while creating the Batch"+ e);
			
			return -1;
		}
	}
    public Collection<BatchDto> getBatchDetail(){
		
		
		
		try {
			
			return importHistoryDao.getBatchDetail();
		} catch (Exception e) {
		
			
			return null;
		}
	}


		
  //Author: Sagun Saluja

public Integer getTotalTargets(){
	
	LOGGER.debug("Request received from Control to get Total Targets");
	LOGGER.debug("In CandidatesTrained Service, to get Total Targets ");
	
	try {
		
		LOGGER.debug("In try block to get Total Targets");
		return importHistoryDao.getTotalTargets();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Total Targets"+ e);
		return null;
	}
}

public Integer getTargetAchieved(){
	
	LOGGER.debug("Request received from Control to get Target Achieved");
	LOGGER.debug("In CandidatesTrained Service, to get Target Achieved ");
	
	try {
		
		LOGGER.debug("In try block to get Target Achieved");
		return importHistoryDao.getTargetAchieved();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Target Achieved"+ e);
		return null;
	}
}
public Integer getRemainingTargets(){
	
	LOGGER.debug("Request received from Control to get Remaining Targets");
	LOGGER.debug("In CandidatesTrained Service, to get Remaining Targets ");
	
	try {
		
		LOGGER.debug("In try block to get Remaining Targets");
		return importHistoryDao.getRemainingTargets();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the Remaining Targets"+ e);
		return null;
	}
}
public Integer getFinancialYear(){
	
	LOGGER.debug("Request received from Control to get FinancialYear");
	LOGGER.debug("In CandidatesTrained Service, to get FinancialYear ");
	
	try {
		
		LOGGER.debug("In try block to get Remaining Targets");
		return importHistoryDao.ShowFinancialYear();
	}
	catch (Exception e) {
	
		LOGGER.error("An error occurred while getting the FinancialYear"+ e);
		return null;
	}
}
}
