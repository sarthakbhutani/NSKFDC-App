package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="insertBatchNumber",locations="classpath:sql/insertBatchNumber.yml")
public class InsertBatchNumberConfig {

	private String checkBatchNumberExistence;
	private String updateBatchNumber;
	private String showBatchNumberDetails;
	private String checkBatchNumberForBatchId;
	public String getCheckBatchNumberExistence() {
		return checkBatchNumberExistence;
	}
	public void setCheckBatchNumberExistence(String checkBatchNumberExistence) {
		this.checkBatchNumberExistence = checkBatchNumberExistence;
	}
	public String getUpdateBatchNumber() {
		return updateBatchNumber;
	}
	public void setUpdateBatchNumber(String updateBatchNumber) {
		this.updateBatchNumber = updateBatchNumber;
	}
	public String getShowBatchNumberDetails() {
		return showBatchNumberDetails;
	}
	public void setShowBatchNumberDetails(String showBatchNumberDetails) {
		this.showBatchNumberDetails = showBatchNumberDetails;
	}
	public String getCheckBatchNumberForBatchId() {
		return checkBatchNumberForBatchId;
	}
	public void setCheckBatchNumberForBatchId(String checkBatchNumberForBatchId) {
		this.checkBatchNumberForBatchId = checkBatchNumberForBatchId;
	}
	public InsertBatchNumberConfig(String checkBatchNumberExistence, String updateBatchNumber,
			String showBatchNumberDetails, String checkBatchNumberForBatchId) {
		super();
		this.checkBatchNumberExistence = checkBatchNumberExistence;
		this.updateBatchNumber = updateBatchNumber;
		this.showBatchNumberDetails = showBatchNumberDetails;
		this.checkBatchNumberForBatchId = checkBatchNumberForBatchId;
	}
	public InsertBatchNumberConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
		
}
