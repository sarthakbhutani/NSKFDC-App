package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class ViewDocumentDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String trainingPartnerName;
	private String uplodedOn;
	private String zipFileLink;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	public String getUplodedOn() {
		return uplodedOn;
	}
	public void setUplodedOn(String uplodedOn) {
		this.uplodedOn = uplodedOn;
	}
	public String getZipFileLink() {
		return zipFileLink;
	}
	public void setZipFileLink(String zipFileLink) {
		this.zipFileLink = zipFileLink;
	}
				public ViewDocumentDto(String batchId, String trainingPartnerName,
						String uplodedOn, String zipFileLink) {
						super();
						this.batchId = batchId;
						this.trainingPartnerName = trainingPartnerName;
						this.uplodedOn = uplodedOn;
						this.zipFileLink = zipFileLink;
				}
				
				public ViewDocumentDto() {
					super();
					// TODO Auto-generated constructor stub
				}
	
	
	
	
}
	
	