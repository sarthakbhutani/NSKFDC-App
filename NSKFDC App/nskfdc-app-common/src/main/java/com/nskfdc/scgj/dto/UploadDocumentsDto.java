package com.nskfdc.scgj.dto;


import com.nskfdc.scgj.common.BaseDto;

public class UploadDocumentsDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public void setDocumentsUploaded(StringBuilder documentsUploaded) {
		this.documentsUploaded = documentsUploaded;
	}
	/**
	 * Sting that hold link of zip file
	 * @return
	 */
	public String getZipFileLink() {
		return zipFileLink;
	}
	public void setZipFileLink(String zipFileLink) {
		this.zipFileLink = zipFileLink;
	}
	public UploadDocumentsDto(String batchId, String dateUploaded, StringBuilder documentsUploaded, String zipFileLink) {
		super();
		this.batchId = batchId;
		this.dateUploaded = dateUploaded;
		this.documentsUploaded = documentsUploaded;
		this.zipFileLink = zipFileLink;
	}
	/**
	 * Default Constructor
	 */
	public UploadDocumentsDto() {
		super();
	}
	
	
}
