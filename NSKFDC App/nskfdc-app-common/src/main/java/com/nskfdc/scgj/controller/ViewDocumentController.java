package com.nskfdc.scgj.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.ViewDocumentDto;
import com.nskfdc.scgj.service.ViewDocumentService;

@RestController
public class ViewDocumentController {
	
private static final Logger LOGGER= LoggerFactory.getLogger(ViewDocumentController.class);
	
	@Autowired
	private ViewDocumentService viewDocumentService;
	
	@RequestMapping("/getTrainingPartnerDetailForSearchbatchId")
	public Collection<ViewDocumentDto>getTrainingPartnerDetailForSearchbatchId(@RequestParam("tpName") String tpName, @RequestParam("batchId") String batchId){

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In  ViewDocument Controller");
		
		try {
			LOGGER.debug("In try block to get training partner details for Search");
			LOGGER.debug("Sending request to service");
			 return viewDocumentService.getViewTrainingPartnerDetailForBatchId(tpName,batchId);
			
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
			return null;
		}
		
	}
	
	@RequestMapping("/getTrainingPartnerDetailForSearchscgjBtNumber")
	public Collection<ViewDocumentDto>getTrainingPartnerDetailForSearchscgjBtNumber(@RequestParam("tpName") String tpName, @RequestParam("scgjBtNumber") String scgjBtNumber){

		LOGGER.debug("Request received from frontend");
		LOGGER.debug("In  ViewDocument Controller");
		
		try {
			LOGGER.debug("In try block to get training partner details for Search");
			LOGGER.debug("Sending request to service");
			 return viewDocumentService.getViewTrainingPartnerDetailForscgjBtNumber(tpName , scgjBtNumber);
			
		} catch (Exception e) {
		
			LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
			return null;
		}
		
	}
//	@RequestMapping("/getFilePathsForCreateZipFile")
//	public ArrayList<String>getFilePathsForCreateZipFile(){
//
//		LOGGER.debug("Request received from frontend");
//		LOGGER.debug("In  ViewDocument Controller");
//		
//		try {
//			LOGGER.debug("In try block to get training partner details for Search");
//			LOGGER.debug("Sending request to service");
//			 			return viewDocumentService.CreateZipFile();
//			
//		} catch (Exception e) {
//		
//			LOGGER.debug("An error occurred while getting the training partner details for Search"+ e);
//			return null;
//		}
//		
//	}

}
