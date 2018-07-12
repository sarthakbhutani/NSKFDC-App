package com.nskfdc.scgj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.nskfdc.scgj.dao.GenerateBatchReportDao;

@Component
@ConfigurationProperties(prefix="batchIdQuery" , locations="classpath:sql/getBatchId.yml")
public class GenerateBatchReportConfig {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GenerateBatchReportDao.class);
	private String showBatchId;

	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Getter Method to receive Query from YML file. 
	 *@return  The query in YML file.
	 
	 **/
	
	public String getShowBatchId() {
		LOGGER.debug("Entered the Get Block of Config "+showBatchId);
		return showBatchId;
	}

	/**
	 
	 *@author Samridhi Srivastava
	 *@description This method is a Setter Method to set Query from YML file. 
	 *@return  NIL.
	 
	 **/
	
	public void setShowBatchId(String showBatchId) {
		this.showBatchId = showBatchId;
	}
	
	
}
