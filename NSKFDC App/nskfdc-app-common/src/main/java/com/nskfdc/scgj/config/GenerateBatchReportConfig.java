package com.nskfdc.scgj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.nskfdc.scgj.dao.GenerateBatchReportDao;

@Component
@ConfigurationProperties(prefix="batchIdQuery" , locations="classpath:sql/generateBatchReport.yml")
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
	
	private String showLocationDetails;

	public String getShowLocationDetails() {
		return showLocationDetails;
	}

	public void setShowLocationDetails(String showLocationDetails) {
		this.showLocationDetails = showLocationDetails;
	}
	
	private String showTrainingDetails;

	public String getShowTrainingDetails() {
		return showTrainingDetails;
	}

	public void setShowTrainingDetails(String showTrainingDetails) {
		this.showTrainingDetails = showTrainingDetails;
	}
	
	private String showCandidateDetails;

	public String getShowCandidateDetails() {
		return showCandidateDetails;
	}

	public void setShowCandidateDetails(String showCandidateDetails) {
		this.showCandidateDetails = showCandidateDetails;
	}
	
	private String showUpdateBatchNumber;

	public String getShowUpdateBatchNumber() {
		LOGGER.debug("Entered the Get Block of Config "+showUpdateBatchNumber);
		return showUpdateBatchNumber;
	}

	public void setShowUpdateBatchNumber(String showUpdateBatchNumber) {
		this.showUpdateBatchNumber = showUpdateBatchNumber;
	}

	private String showReport;
	
	public String getShowReport() {
		return showReport;
	}

	public void setShowReport(String showReport) {
		this.showReport = showReport;
	}

	
	
	
	
}
