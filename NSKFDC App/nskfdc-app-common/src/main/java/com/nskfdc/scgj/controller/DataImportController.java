package com.nskfdc.scgj.controller;

import java.util.Collection;
import java.io.*;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.BatchDto;
import com.nskfdc.scgj.dto.GetBatchDetailsDto;
import com.nskfdc.scgj.dto.MasterSheetSubmitDto;
import com.nskfdc.scgj.service.DataImportService;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class DataImportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(DataImportController.class);
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	@Autowired
	private DataImportService dataImportService;


	/*---------- Master Sheet Import --------------*/

	  @RequestMapping(value="/importMasterSheet",method = RequestMethod.POST,consumes = MediaType.ALL_VALUE)
	  public String masterSheetImport(@RequestParam("file") MultipartFile file, @RequestParam("batchId") int batchId) throws FileNotFoundException
	  {
		  LOGGER.debug("In Data import controller to read excel file");
		  try
		  {
			  if(file == null)
			  {
				  LOGGER.debug("Null File in controller");
				  return "File is null";
			  }
			  else if(batchId < 1)
			  {
				  LOGGER.error("BatchID is null");
				  return "batchID is null";
			  }
			  else
			  return dataImportService.masterSheetImport(file,batchId);
		  }

		  catch(Exception e)
		  {
			  LOGGER.error("Exception is " + e);
			  return null;
		  }
	  }

	@RequestMapping("/BatchDetails")
	public GetBatchDetailsDto BatchDetails(@RequestParam("batchId") String batchId){
	
		try {
	
			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();		
			LOGGER.debug("Email is " + userEmail);
			LOGGER.debug("Trying to get details for the corresponding batch id:"+ batchId+ "to display on master import sheet tab");
			LOGGER.debug("Sending request to service to get batch details by id :" + userEmail);	
			return  dataImportService.BatchDetails(userEmail,batchId);
			
		}
		catch(Exception e) {	
			LOGGER.debug("An error occurred while searching for batch details" + e);
			LOGGER.debug("Returning NULL!");
			return null;
			
		}	
	}
              
	@RequestMapping("/getTotalTargets")
     public Integer getTotalTargets(){
                     String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
                     LOGGER.debug("Request received from frontend to get Total Targets");
                     LOGGER.debug("In get Total Targets Controller");
                     
                     try {
                           
                           LOGGER.debug("In try block to get Total Targets");
                           LOGGER.debug("Sending request to service");
                           return dataImportService.getTotalTargets(userEmail);
                           
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
                           return dataImportService.getTargetAchieved(userEmail);
                           
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
                           return dataImportService.getRemainingTargets(userEmail);
                           
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
                                  return dataImportService.getFinancialYear(userEmail);
                                  
                                  }
                           catch(Exception e) {
                                  
                                  LOGGER.error("An exception occurred while getting the FinancialYear" + e);
                                  return null;
                                  
                  }
              }
                     
                     @RequestMapping("/downloadFinalMasterSheet")
                 	public void downloadMasterSheetController(HttpServletResponse response){
                 		
                 		LOGGER.debug("Request received from frontend");
                 		LOGGER.debug("In downloadMasterSheetController");
                 		
                 		try {
                 			
                 			LOGGER.debug("In try block of generate Master Sheet Controller");
                 			
                 		   String userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
                 		   String report = dataImportService.downloadMasterSheetService(userEmail);
                 			
                 			if(report!=null) {
                 				
                 				LOGGER.debug("Creating object of File");
                 				File file = new File(report);
                 			
                 				LOGGER.debug("Setting Content Type and Header");
                 				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                 				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
                 		    
                 				LOGGER.debug("Creating object of BufferedInputStream, BufferedOutputStream");
                 		    	BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
                 		    	BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());  
                 		    	byte[] buffer = new byte[1024];
                 		    	int bytesRead = 0;
                 		    	while ((bytesRead = inStrem.read(buffer)) != -1) {
                 		    		outStream.write(buffer, 0, bytesRead);
                 		    	}
                 		    
                 		    	LOGGER.debug("Excel Sheet Generated successfully");
                 		    
                 		    	outStream.flush();
                 		    	inStrem.close();
                 			} else {
                 				LOGGER.debug("Path not found");
                 			}
                 		}catch(Exception e) {
                 			LOGGER.error("In catch block of downloadMasterSheetController"+e);
                 		}	
                 	}	      
                     /*--------------------Method to Generate BatchId-------------------*/
                	 
                 	@RequestMapping("/generateBatch")
                 	 public Integer generateBatchController(){
                 		 String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();
                 		 LOGGER.debug("Request received from frontend to create batch for email id : " + userEmail);
                 		
                 		 LOGGER.debug("In Import Controller to create batch for email id: " + userEmail);
                 		 try{
                 			
                 			 return dataImportService.getGenerateBatchService(userEmail);
                 				
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
            				return dataImportService.getBatchDetail(userEmail);
            				
            			}
            			catch(Exception e) {
            				
            			
            				return null;
            				
            			}
            		}
                 	
            		
            		
            		
            /*---------------Method to save batch Details in the database-------------*/
            		@RequestMapping(value="/submitBatchDetails", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
            		public int submitBatchDetails(@RequestBody MasterSheetSubmitDto masterSheetSubmitDto) {
            			String userEmail = sessionUserUtility.getSessionMangementfromSession().getUsername();            		
            			return dataImportService.submitBatchDetails(userEmail, masterSheetSubmitDto);
            		}
            		
	
}
