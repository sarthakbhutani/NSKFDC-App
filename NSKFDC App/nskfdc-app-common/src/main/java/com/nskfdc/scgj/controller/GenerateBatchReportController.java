package com.nskfdc.scgj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nskfdc.scgj.common.SessionUserUtility;
import com.nskfdc.scgj.dto.CandidateDetailsDto;
import com.nskfdc.scgj.dto.GetBatchIdDto;
import com.nskfdc.scgj.dto.LocationDetailsDto;
import com.nskfdc.scgj.dto.SearchReportDto;
import com.nskfdc.scgj.dto.TrainingDetailsDto;
import com.nskfdc.scgj.service.GenerateBatchReportService;

@RestController
public class GenerateBatchReportController {

	@Autowired
	public GenerateBatchReportService generateBatchReportService;
	
	@Autowired
	private SessionUserUtility sessionUserUtility;
	
	private String userEmail;
	private Logger LOGGER = LoggerFactory.getLogger(GenerateBatchReportController.class);
	
	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Controller Method that maps the request for getting batch Ids of the particular SCGJ Batch Number. 
	 *@return A Collection of the Batch Ids corresponding to the particular SCGJ Batch Number.
	 
	 **/
	
	@RequestMapping("/getBatchIdDetails")
	public Collection<GetBatchIdDto> getBatchIdDetails(){
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In GetBatchId Controller");
		
		
		try {
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			LOGGER.debug("In try block to get training partner details for Training Partner");
			LOGGER.debug("Sending request to Service");
			return generateBatchReportService.getBatchDetails(userEmail);
			
		} catch (Exception e) {
			LOGGER.error("An error occurred while getting the training partner details for Training Partner");
			return null;
		}
	}
	@RequestMapping("/getLocationDetails")
	public Collection<LocationDetailsDto> locationDetails(@RequestParam ("batchId") String batchId){
		try{
			return generateBatchReportService.locationDetails(batchId);
		}
		catch (Exception e) {
			return null;
		}
	}
	@RequestMapping("/getTrainingDetails")
	public Collection<TrainingDetailsDto> trainingDetails(@RequestParam ("batchId") String batchId){
		try{
			return generateBatchReportService.trainingDetails(batchId);
		}
		catch (Exception e) {
			return null;
		}
	}
	@RequestMapping("/getCandidateDetails")
	public Collection<CandidateDetailsDto> candidateDetails(@RequestParam ("batchId") String batchId){
		try{
			return generateBatchReportService.candidateDetails(batchId);
		}
		catch (Exception e) {
			return null;
		}
	}
	@RequestMapping("/embedimages")
	public int embeddimages(@RequestParam ("file") MultipartFile file){
		try{
			return generateBatchReportService.embeddimages(file);
		}
		catch(Exception e){
			return -1;
		}
	}
	
	@RequestMapping("/generateBatchReport")
	public void batchReport(@RequestParam ("batchId") String batchId,@RequestParam ("batchnumber") String batchnumber,HttpServletResponse response){
		String report;
		userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
		try{
			report= generateBatchReportService.generateBatchReport(batchId,batchnumber,userEmail);
if(report!=null) {
				
				LOGGER.debug("Creating object of File");
				File file = new File(report);
			
				LOGGER.debug("Setting Content Type and Header");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		    
				LOGGER.debug("Creating object of BufferedInputStream, BufferedOutputStream");
		    	BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
		    	BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());  
		    	byte[] buffer = new byte[1024];
		    	int bytesRead = 0;
		    	while ((bytesRead = inStrem.read(buffer)) != -1) {
		    		outStream.write(buffer, 0, bytesRead);
		    	}
		    
		    	LOGGER.debug("Pdf Generated successfully");
		    
		    	outStream.flush();
		    	inStrem.close();
			} else {
				LOGGER.debug("Path not found");
			}
		}
		
		catch (Exception e) {
			LOGGER.debug("Error Occurred"+e);
		}
	}
	
	
	
	/**
	 
	 *@author shivangi singh
	 *@description This method is a Controller Method that maps the request for getting reports of particular batchids.. 
	 **/
	
	@RequestMapping("/getReports")
	public Collection<SearchReportDto> getReport(@RequestParam("batchId") String batchId, @RequestParam("userEmail") String userEmail,HttpServletResponse response){

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate batchreport Controller");
		
		try {
			userEmail=sessionUserUtility.getSessionMangementfromSession().getUsername();
			
			LOGGER.debug("In try block to get BATCH detail");
			LOGGER.debug("Sending request to service");
			return generateBatchReportService.getReport(batchId,userEmail);
			
		}catch(Exception e) {
			
			LOGGER.error("An error occurred while getting the REPORT" + e);
			return null;
			
		}
	}
}
