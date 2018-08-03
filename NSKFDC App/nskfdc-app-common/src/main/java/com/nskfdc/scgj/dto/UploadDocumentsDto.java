package com.nskfdc.scgj.dto;


import com.nskfdc.scgj.common.BaseDto;

public class UploadDocumentsDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int batchId;
	String dateUploaded;
	StringBuilder documentsUploaded;
	String zipFileLink;
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
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
	public void setDocumentsUploaded(StringBuilder documentsUploaded) {
		this.documentsUploaded = documentsUploaded;
	}
	public String getZipFileLink() {
		return zipFileLink;
	}
	public void setZipFileLink(String zipFileLink) {
		this.zipFileLink = zipFileLink;
	}
	public UploadDocumentsDto(int batchId, String dateUploaded, StringBuilder documentsUploaded, String zipFileLink) {
		super();
		this.batchId = batchId;
		this.dateUploaded = dateUploaded;
		this.documentsUploaded = documentsUploaded;
		this.zipFileLink = zipFileLink;
	}
	public UploadDocumentsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
