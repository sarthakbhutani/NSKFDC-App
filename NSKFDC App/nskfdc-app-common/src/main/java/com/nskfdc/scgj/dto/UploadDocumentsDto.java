package com.nskfdc.scgj.dto;


import com.nskfdc.scgj.common.BaseDto;

public class UploadDocumentsDto extends BaseDto{

	String batchId;
	String dateUploaded;
	StringBuilder documentsUploaded;
	String zipFileLink;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public StringBuilder getDocumentsUploaded() {
		return documentsUploaded;
	}
	public void setDocumentsUploaded(StringBuilder s2) {
		this.documentsUploaded = s2;
	}
	public String getZipFileLink() {
		return zipFileLink;
	}
	public void setZipFileLink(String zipFileLink) {
		this.zipFileLink = zipFileLink;
	}
	public UploadDocumentsDto(String batchId, String dateUploaded,
			StringBuilder documentsUploaded, String zipFileLink) {
		super();
		this.batchId = batchId;
		this.dateUploaded = dateUploaded;
		this.documentsUploaded = documentsUploaded;
		this.zipFileLink = zipFileLink;
	}
}
