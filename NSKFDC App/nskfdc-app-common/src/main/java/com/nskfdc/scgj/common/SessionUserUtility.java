package com.nskfdc.scgj.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nskfdc.scgj.dto.SessionManagementDto;




@Service
public class SessionUserUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionUserUtility.class);
	
	/*To get the userEmail from session, in your controller get userEmail in a String variable using
	*sessionUserUtility.getSessionMangementfromSession().getUsername(); in your controller method
	*/
	
	 public SessionManagementDto getSessionMangementfromSession(){
		 	LOGGER.debug("Request Received to get Session");
		 	
		 	LOGGER.debug("Sending Request to get SessionManagementDto");
		 	return (SessionManagementDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		
	 }
		
	
}
