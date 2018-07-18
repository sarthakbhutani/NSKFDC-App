package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.service.DataImportService;

@RestController
public class DataImportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportController.class);

	
	@Autowired
	private DataImportService importHistoryService;
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	
	
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
	


	 @RequestMapping("/generateBatch")
	 public int generateBatchController(){
		 String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
		 LOGGER.debug("Request received from frontend to create batch for email id : " + userEmail);
		
		 LOGGER.debug("In Import Controller to create batch for rmail id: " + userEmail);
		 try{
			
			 return importHistoryService.getGenerateBatchService(userEmail);
				
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
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Request received from frontend to show batch ID");
			
			
			try {
				return importHistoryService.getBatchDetail(userEmail);
				
			}
			catch(Exception e) {
				
			
				return null;
				
			}
		}
		
		//Author:Sagun Saluja
		
		@RequestMapping("/getTotalTargets")
		public Integer getTotalTargets(){
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Request received from frontend to get Total Targets");
			LOGGER.debug("In get Total Targets Controller");
			
			try {
				
				LOGGER.debug("In try block to get Total Targets");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getTotalTargets(userEmail);
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Total Targets" + e);
				return null;
				
								}
		}
		
		@RequestMapping("/getTargetAchieved")
		public Integer getTargetAchieveds(){
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Request received from frontend to get Target Achieved");
			LOGGER.debug("In get Target Achieved Controller");
			
			try {
				
				LOGGER.debug("In try block to get Target Achieved");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getTargetAchieved(userEmail);
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Target Achieved" + e);
				return null;
				
								}
		}
		
		
		
		@RequestMapping("/getRemainingTargets")
		
		public Integer getRemainingTargets(){
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("Request received from frontend to get Remaining Target");
			LOGGER.debug("In get Remaining Target Controller");
			
			try {
				
				LOGGER.debug("In try block to get Remaining Target");
				LOGGER.debug("Sending request to service");
				return importHistoryService.getRemainingTargets(userEmail);
				
				}
			catch(Exception e) {
				
				LOGGER.error("An exception occurred while getting the Target Achieved" + e);
				return null;
				
								}
		}
			@RequestMapping("/getFinancialYear")
			public Integer getFinancialYear(){
				String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
				LOGGER.debug("Request received from frontend to get FinancialYear");
				LOGGER.debug("In get Remaining Target Controller");
				
				try {
					
					LOGGER.debug("In try block to get FinancialYear");
					LOGGER.debug("Sending request to service");
					return importHistoryService.getFinancialYear(userEmail);
					
					}
				catch(Exception e) {
					
					LOGGER.error("An exception occurred while getting the FinancialYear" + e);
					return null;
					
									}
		}
}
