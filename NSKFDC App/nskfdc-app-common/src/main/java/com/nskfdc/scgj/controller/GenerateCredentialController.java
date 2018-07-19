package com.nskfdc.scgj.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nskfdc.scgj.dto.GenerateCredentialDto;
import com.nskfdc.scgj.dto.GenerateCredentialSearchDto;
import com.nskfdc.scgj.service.GenerateCredentialService;


@RestController
public class GenerateCredentialController {

	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateCredentialController.class);
	@Autowired
	private GenerateCredentialService generatecredentialService;
	
	 @RequestMapping(value="/getGenerateCredential",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public Integer generatecredentialController(@RequestBody GenerateCredentialDto generateCredentialDto){
	 
		 LOGGER.debug("Request received from frontend to generate credential" + generateCredentialDto.getJobRole());
		
		 LOGGER.debug("In GenerateCredential Controller to generate credential");

	 try{
		
		 return generatecredentialService.checkUserExistence(generateCredentialDto);				
	 }
		
		 catch(Exception e) {
		
			 LOGGER.error("An error occurred while generating credentials" + e);
				
			 return -1;
		 }

	 }
	
		@RequestMapping("/SearchService")
		public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(@RequestParam("nsdcRegNumber") String nsdcRegNumber){
			LOGGER.debug("Request received from frontend");
			LOGGER.debug("In generate credential search  Controller");
			
			try {
				
				LOGGER.debug("In try block to get training partner details ");
				LOGGER.debug("Sending request to service");
				return generatecredentialService.getTrainingPartnerDetail(nsdcRegNumber);
				
			}catch(Exception e) {
				
				LOGGER.debug("An error occurred while getting the training partner details " + e);
				return null;
				
			}
		}
}



