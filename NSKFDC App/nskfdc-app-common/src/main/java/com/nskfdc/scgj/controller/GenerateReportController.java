package com.nskfdc.scgj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nskfdc.scgj.service.GenerateReportService;

@RestController
public class GenerateReportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateReportController.class);
	
	@Autowired
	private GenerateReportService generateReportService;
	int success;
	
	@RequestMapping("/generateOccupationCertificateReport")
	public String generateOccupationCertificateReport(@RequestParam("batchId") String batchId) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Occupation Certificate Controller");
		
		try {
			
			LOGGER.debug("In try block of Generate Occupation Certificate Controller");
			success = generateReportService.generateOccupationCertificateReport(batchId);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Occupation Certificate Controller"+e);
		}
		
		if(success==1) 
			return "Occupation Certificate generated successfully";
		else
			return "Occupation Certificate not generated";
	}

	@RequestMapping("/generateAttendanceSheet")
	public String generateAttendanceSheet(@RequestParam("batchId") String batchId) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Attendance Sheet Controller");
		
		try {		
			
			LOGGER.debug("In try block of Generate Attendance Sheet Controller");
			success = generateReportService.generateAttendanceSheet(batchId);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Attendance Sheet Controller"+e);
		}
		
		if(success==1) 
	    	return "Attendance Sheet generated successfully";
	    else
	    	return "Attendance Sheet not generated";
	}
	
}
