package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="generateCredential",locations="classpath:sql/GenerateCredential.yml")
public class GenerateCredentialConfig {

	private String usertableCredential;
	private String trainingPartnerCredentials;
	private String checkUserExistence;
	private String checkNsdcNumberExistence;
	
	
	
	public String getCheckUserExistence() {
		return checkUserExistence;
	}
	public void setCheckUserExistence(String checkUserExistence) {
		this.checkUserExistence = checkUserExistence;
	}
	
	public String getUsertableCredential() {
		return usertableCredential;
	}
	public void setUsertableCredential(String usertableCredential) {
		this.usertableCredential = usertableCredential;
	}
	public String getTrainingPartnerCredentials() {
		return trainingPartnerCredentials;
	}
	public void setTrainingPartnerCredentials(String trainingPartnerCredentials) {
		this.trainingPartnerCredentials = trainingPartnerCredentials;
	}

	private String showTrainingPartnerDetails;

	public String getShowTrainingPartnerDetails() {
		return showTrainingPartnerDetails;
	}

	public void setShowTrainingPartnerDetails(String showTrainingPartnerDetails) {
		this.showTrainingPartnerDetails = showTrainingPartnerDetails;
	}
	public String getCheckNsdcNumberExistence() {
		return checkNsdcNumberExistence;
	}
	public void setCheckNsdcNumberExistence(String checkNsdcNumberExistence) {
		this.checkNsdcNumberExistence = checkNsdcNumberExistence;
	}
   





}





