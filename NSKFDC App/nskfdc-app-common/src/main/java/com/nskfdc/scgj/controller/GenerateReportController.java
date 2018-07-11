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
	public int generateOccupationCertificateReport(@RequestParam("batchId") String batchId, @RequestParam("trainingPartnerEmail") String trainingPartnerEmail) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Occupation Certificate Controller");
		
		try {
			
			LOGGER.debug("In try block of Generate Occupation Certificate Controller");
			success = generateReportService.generateOccupationCertificateReport(batchId,trainingPartnerEmail);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Occupation Certificate Controller"+e);
		}
		
		return success;
	}

	@RequestMapping("/generateAttendanceSheet")
	public int generateAttendanceSheet(@RequestParam("batchId") String batchId,  @RequestParam("trainingPartnerEmail") String trainingPartnerEmail) {
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In Generate Attendance Sheet Controller");
		
		try {		
			
			LOGGER.debug("In try block of Generate Attendance Sheet Controller");
			success = generateReportService.generateAttendanceSheet(batchId,trainingPartnerEmail);	
			
		}catch(Exception e) {
			LOGGER.error("In catch block of Generate Attendance Sheet Controller"+e);
		}
		
		return success;
	}
	
}
