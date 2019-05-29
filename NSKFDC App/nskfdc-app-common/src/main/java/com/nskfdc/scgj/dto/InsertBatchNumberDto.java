package com.nskfdc.scgj.dto;

import java.sql.Date;

import com.nskfdc.scgj.common.BaseDto;

public class InsertBatchNumberDto extends BaseDto{

	private String batchId;
	private String scgjBatchNumber;
	private Date updatedOn;
	private String trainingPartnerName;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getScgjBatchNumber() {
		return scgjBatchNumber;
	}
	public void setScgjBatchNumber(String scgjBatchNumber) {
		this.scgjBatchNumber = scgjBatchNumber;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	public InsertBatchNumberDto(String batchId, String scgjBatchNumber, Date updatedOn, String trainingPartnerName) {
		super();
		this.batchId = batchId;
		this.scgjBatchNumber = scgjBatchNumber;
		this.updatedOn = updatedOn;
		this.trainingPartnerName = trainingPartnerName;
	}
	public InsertBatchNumberDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
