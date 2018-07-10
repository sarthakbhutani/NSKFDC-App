package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.SearchReportDto;
import com.nskfdc.scgj.service.SearchReportService;





@RestController
public class SearchReportController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(SearchReportController.class);
	
	@Autowired
	private SearchReportService searchreportService;

	@RequestMapping("/getReports")
	public Collection<SearchReportDto> getReport(@RequestParam("batchId") int batchId){
		
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("");
		
		try {
			
			LOGGER.debug("In try block to get BATCH detail");
			LOGGER.debug("Sending request to service");
			return searchreportService.getReport(batchId);
			
		}catch(Exception e) {
			
			LOGGER.debug("An error occurred while getting the REPORT" + e);
			return null;
			
		}
	}}


