package com.nskfdc.scgj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.service.ImportService;

@RestController
public class ImportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ImportController.class);

	
	@Autowired
	private ImportService importHistoryService;
	
	@RequestMapping("/importMasterSheet")
	public void importMasterSheet() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				//change return type
				importHistoryService.importMasterSheet();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}
	
	@RequestMapping("/getImportHistory")
	public void getImportHistory() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				// change return type
				importHistoryService.getImportHistory();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}
	
	@RequestMapping("/searchMasterSheet")
	public void searchMasterSheet() {
		
		//write LOGGER here
			
			try {
				//write LOGGER here		
				// change return type
				importHistoryService.getSearchedMasterSheet();
				
			} catch (Exception e) {
				
				//write LOGGER here
				//return the default value, it can be null
			}
		}

	 @RequestMapping("/getDataImport")
	 public int generateBatchController(){
		 String trainingPartnerEmail = "snb@gmail.com";
		 LOGGER.debug("Request received from frontend to create batch for email id : " + trainingPartnerEmail);
		
		 LOGGER.debug("In Import Controller to create batch for rmail id: " + trainingPartnerEmail);
		 try{
			
			 return importHistoryService.getGenerateBatchService(trainingPartnerEmail);
				
		 }
		
		 catch(DataAccessException d) {
			
			 LOGGER.error("DataAccessException in controller to create Batch" + d);
			
			 return -1;
		 }
		
		 catch(Exception e) {
		
			 LOGGER.error("An error occurred while creating Batch" + e);
				
			 return -1;
		 }

	 }

}
