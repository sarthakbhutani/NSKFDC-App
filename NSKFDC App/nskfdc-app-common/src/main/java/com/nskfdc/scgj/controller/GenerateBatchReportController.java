package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.service.GenerateBatchReportService;

@RestController
public class GenerateBatchReportController {

	@Autowired
	public GenerateBatchReportService generateBatchReportService;
	
	private Logger LOGGER = LoggerFactory.getLogger(GenerateBatchReportController.class);
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Controller Method that maps the request for getting batch Ids of the particular SCGJ Batch Number. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	@RequestMapping("/getBatchIdDetails")
	public Collection<GetBatchIdDto> getBatchIdDetails(@RequestParam ("batchnumber") String batchNumber){
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In GetBatchId Controller");
		
		try {
			
			LOGGER.debug("In try block to get training partner details for Training Partner");
			LOGGER.debug("Sending request to Service");
			return generateBatchReportService.getBatchDetails(batchNumber);
			
		} catch (Exception e) {
			LOGGER.error("An error occurred while getting the training partner details for Training Partner");
			return null;
		}
	}
}
