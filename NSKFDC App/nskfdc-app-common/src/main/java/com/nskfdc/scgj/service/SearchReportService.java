package com.nskfdc.scgj.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dao.SearchReportDao;
import com.nskfdc.scgj.dto.SearchReportDto;





@Service
public class SearchReportService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchReportService.class);
	
	@Autowired
	private SearchReportDao searchreportDao;
	
	public Collection<SearchReportDto> getReport(int batchId){
		
		LOGGER.debug("REQUEST RECEIVED");
		LOGGER.debug("GET REPORT");
		
		try {
			LOGGER.debug("In try block to get BATCH details");
			
			return searchreportDao.getReport(batchId);
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting REPORT"+ e);
			return null;
		}
	}}

