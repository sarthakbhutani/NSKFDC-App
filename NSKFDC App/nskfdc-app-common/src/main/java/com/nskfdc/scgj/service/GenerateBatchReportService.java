package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.GenerateBatchReportDao;
import com.nskfdc.scgj.dto.GetBatchIdDto;;

@Service
public class GenerateBatchReportService {
	
	@Autowired
	private GenerateBatchReportDao generateBatchReportDao;
	
	private Logger LOGGER = LoggerFactory.getLogger(GenerateBatchReportService.class);
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Service Method that gets the batch Ids of the particular SCGJ Batch Number which is being passed as parameters. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	public Collection<GetBatchIdDto> getBatchDetails(String batchNumber){
		LOGGER.debug("Request received from Controller");
		LOGGER.debug("In Get Batch Id Service, to get Batch Ids' for Training Partner");
		
	try{
		
		LOGGER.debug("In try block to get training partner details for Training Partner");
		LOGGER.debug("Sending request to Dao");
		return generateBatchReportDao.getBatchId(batchNumber);
	}
	catch(Exception e){
		
		LOGGER.error("An error occurred while getting the training partner details for Training Partner");
		return null;
	}
  }
}
