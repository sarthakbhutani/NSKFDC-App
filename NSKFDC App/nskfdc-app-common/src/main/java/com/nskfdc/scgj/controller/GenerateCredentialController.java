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
	 
		 LOGGER.debug("Request received from frontend");
		 LOGGER.debug("In GenerateCredential Controller");
		 LOGGER.debug("To generate credential");
		 

	 try{
		LOGGER.debug("TRYING -- generatecredentialController to generate credential");
		LOGGER.debug("Sending Request to service - generatecredentialService");
		 return generatecredentialService.checkUserExistence(generateCredentialDto);
	 }
		
		 catch(Exception e) {
			 LOGGER.error("CATCHING -- Exception handled while Generating Credential");
			 LOGGER.error("In GenerateCredentialController, method - generatecredentialController" + e);
			LOGGER.error("Exception is"+e);
			 return -1;
		 }

	 }
	
		@RequestMapping("/SearchService")
		public Collection<GenerateCredentialSearchDto> getTrainingPartnerDetail(@RequestParam("nsdcRegNumber") String nsdcRegNumber){
			LOGGER.debug("Request received from frontend");
			LOGGER.debug("In getTrainingPartnerDetail");
			LOGGER.debug("To get Generated Credential details");
			LOGGER.debug("For entered NSDC Reg Number");
			
			try {
				
				LOGGER.debug("TRYING -- to get Generated Credential details for searched NSDC Reg Number");
				LOGGER.debug("Sending request to Generate Credential service");
				LOGGER.debug("Method - getTrainingPartnerDetail");
				return generatecredentialService.getTrainingPartnerDetail(nsdcRegNumber);
				
			}catch(Exception e) {
				LOGGER.error("CATCHING -- EXCEPTION handled in GenerateBatchReportController");
				LOGGER.debug("In method - getTrainingPartnerDetail ");
				LOGGER.error("Searching for credentials corresponding entered NSDC Reg Number");
				LOGGER.error("Exception is "+ e);
				return null;
				
			}
		}
}



