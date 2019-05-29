package com.nskfdc.scgj.dto;

import java.util.Date;

import com.nskfdc.scgj.common.BaseDto;

public class DisplayAuditTableRecordDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String reportType;
	private Date generatedOn;
	private String generatedBy;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getType() {
		return reportType;
	}
	public void setType(String reportType) {
		this.reportType = reportType;
	}
	public Date getGeneratedOn() {
		return generatedOn;
	}
	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
	}
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	
	public DisplayAuditTableRecordDto(String batchId,String reportType,Date generatedOn,String generatedBy) {
		this.batchId = batchId;
		this.reportType = reportType;
		this.generatedOn = generatedOn;
		this.generatedBy = generatedBy;
		
	}
	public DisplayAuditTableRecordDto() {
		super();
	}
	
}
