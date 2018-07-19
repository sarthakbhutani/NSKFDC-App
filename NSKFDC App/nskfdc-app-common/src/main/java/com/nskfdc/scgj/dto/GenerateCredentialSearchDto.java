package com.nskfdc.scgj.dto;

import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class GenerateCredentialSearchDto extends BaseDto {
	private String nsdcRegNumber;
	private String trainingPartner;
	private String userEmail;
	private String password;
	private Date generatedOn;
	public String getNsdcRegNumber() {
		return nsdcRegNumber;
	}
	public void setNsdcRegNumber(String nsdcRegNumber) {
		this.nsdcRegNumber = nsdcRegNumber;
	}
	public String getTrainingPartner() {
		return trainingPartner;
	}
	public void setTrainingPartner(String trainingPartner) {
		this.trainingPartner = trainingPartner;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public GenerateCredentialSearchDto (String nsdcRegNumber,String trainingPartner, String userEmail, String password, Date generatedOn) {
		super();
		this.nsdcRegNumber= nsdcRegNumber;
		this.trainingPartner = trainingPartner;
		this.userEmail = userEmail;
		this.password = password;
		this.generatedOn =generatedOn;
		
	}
	
	
	public GenerateCredentialSearchDto () {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getGeneratedOn() {
		return generatedOn;
	}
	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
	}

}
