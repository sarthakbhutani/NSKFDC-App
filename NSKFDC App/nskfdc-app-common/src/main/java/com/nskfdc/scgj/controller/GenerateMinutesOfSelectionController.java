package com.nskfdc.scgj.controller;

//import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//import com.nskfdc.scgj.dto.GenerateMinutesOfSelectionDto;
import com.nskfdc.scgj.service.GenerateMinutesOfSelectionService;




@RestController
public class GenerateMinutesOfSelectionController {
	private static final Logger LOGGER=LoggerFactory.getLogger(GenerateMinutesOfSelectionController.class);

	@Autowired
	private GenerateMinutesOfSelectionService generateMinutesOfSelectionService;
int success;
	
@RequestMapping("/generateMinutesOfSelectionCommitteeDetails")
public int generateMinutesOfSelection( String jobRole,  String trainingPartnerName,String sectorSkillCouncil,String centreCity) {
	
	LOGGER.debug("Request received from frontend");
	LOGGER.debug("In Generate Minutes Of Selection Controller");
	
	try {
		
		LOGGER.debug("In try block of Generate Minutes Of Selection Controller");
		success = generateMinutesOfSelectionService.generateMinutesOfSelection(jobRole,trainingPartnerName,sectorSkillCouncil,centreCity);	
		
	}catch(Exception e) {
		LOGGER.error("In catch block of Generate minutes Of Selection Committee Controller"+e);
	}
	
	return success;
}






	
}
