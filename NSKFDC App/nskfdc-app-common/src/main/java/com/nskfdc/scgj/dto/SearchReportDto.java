package com.nskfdc.scgj.dto;

import java.sql.Date;

import com.nskfdc.scgj.common.BaseDto;



public class SearchReportDto extends BaseDto{
	private int batchid;
	private String type;
	private Date generatedOn;
	private String generatedBy;
	private String viewDocument;
	public int getBatchid() {
		return batchid;
	}
	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	public Date getGeneratedOn() {
		return generatedOn;
	}
	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
	}
	public String getViewDocument() {
		return viewDocument;
	}
	public void setViewDocument(String viewDocument) {
		this.viewDocument = viewDocument;
	}
	
	public SearchReportDto(int batchid,String type, Date generatedOn, String generatedBy,String viewDocument){
		super();
		this.batchid=batchid;
		this.type=type;
		this.generatedOn=generatedOn;
		this.generatedBy=generatedBy;
		this.viewDocument=viewDocument;
		
	}
	
}

