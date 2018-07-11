package com.nskfdc.scgj.dto;



import com.nskfdc.scgj.common.BaseDto;



public class SearchReportDto extends BaseDto{
	private String batchId;  
	//private String generatedOn;
	private String trainingPartnerEmail;
	
	
	 
	
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchid(String batchId) {
		this.batchId = batchId;
	}

	

	

	public String getTrainingPartnerEmail() {
		return trainingPartnerEmail;
	}

	public void setTrainingPartnerEmail(String trainingPartnerEmail) {
		this.trainingPartnerEmail = trainingPartnerEmail;
	}

	public SearchReportDto(String batchId, String trainingPartnerEmail){
		super();
		this.batchId=batchId;
	
		this.trainingPartnerEmail=trainingPartnerEmail;
		
	}
	
}

