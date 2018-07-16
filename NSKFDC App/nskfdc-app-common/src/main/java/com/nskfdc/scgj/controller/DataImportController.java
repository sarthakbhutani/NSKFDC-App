package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.BatchDto;

import com.nskfdc.scgj.service.DataImportService;

@RestController
public class DataImportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportController.class);

	
	@Autowired
	private DataImportService importHistoryService;
	
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

	 @RequestMapping("/generateBatch")
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
		@RequestMapping("/getBatchIdfortrainer")
		public Collection<BatchDto> getBatchDetail(){
			
			LOGGER.debug("Request received from frontend to show batch ID");
			
			
			try {
				
			
				return importHistoryService.getBatchDetail();
				
			}catch(Exception e) {
				
			
				return null;
				
			}
		}
		
		//Author:Sagun Saluja
		
		@RequestMapping("/getTotalTargets")
		public int getTotalTargets(){
			
			LOGGER.debug("Request received from frontend to get Total Targets");
			LOGGER.debug("In get Total Targets Controller");
			
			try {
				
				LOGGER.debug("In try block to get Total Targets");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getTotalTargets();
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Total Targets" + e);
				return 0;
				
								}
		}
		
		@RequestMapping("/getTargetAchieved")
		public int getTargetAchieveds(){
			
			LOGGER.debug("Request received from frontend to get Target Achieved");
			LOGGER.debug("In get Target Achieved Controller");
			
			try {
				
				LOGGER.debug("In try block to get Target Achieved");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getTargetAchieved();
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Target Achieved" + e);
				return 0;
				
								}
		}
		
		
		
		@RequestMapping("/getRemainingTargets")
		public int getRemainingTargets(){
			
			LOGGER.debug("Request received from frontend to get Remaining Target");
			LOGGER.debug("In get Remaining Target Controller");
			
			try {
				
				LOGGER.debug("In try block to get Remaining Target");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getRemainingTargets();
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Target Achieved" + e);
				return 0;
				
								}
		}
}
