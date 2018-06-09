package com.nskfdc.scgj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.service.UploadDocumentService;

@RestController
public class UploadDocumentsController {
	
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

	
}
