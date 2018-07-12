package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.BatchIdDto;
import com.nskfdc.scgj.service.UploadDocumentService;

@RestController
public class UploadDocumentsController {
	private static final Logger LOGGER= LoggerFactory.getLogger(UploadDocumentsController.class);
	
	@Autowired
	private UploadDocumentService uploadDocumentService;
	
	@RequestMapping("/uploadDocuments")
	public void uploadDocuments() {
		
		// Write your code here with loggers
		
		uploadDocumentService.saveUploadedDocument();
	}
	
	@RequestMapping("/uploadHistory")
	public void uploadHistory() {
		
		// Write your code here with loggers
		
		uploadDocumentService.getUploadHistory();
	}
	
	@RequestMapping("/searchDocument")
	public void searchDocument() {
		
		// Write your code here with loggers
		
		uploadDocumentService.getSearchedDocument();
	}
	
	
	@RequestMapping("/getBatchIdDet")
	public Collection<BatchIdDto> getBatchIdDetails(){
		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In upload Controller");
		try{
			LOGGER.debug("In try block of upload documents");
			LOGGER.debug("Sending request to uploadservice");
			
			return uploadDocumentService.getBatchDetails();
		}
		catch(Exception e){
			LOGGER.debug("An error occurred in upload controller" + e);
			return null;
		}
	}
	

	
}
