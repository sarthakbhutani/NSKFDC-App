package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="uploadDocuments",locations="classpath:sql/uploadDocuments.yml")
public class UploadDocumentConfig {
	private String uploadDocumentsQuery;

	public String getUploadDocumentsQuery() {
		return uploadDocumentsQuery;
	}

	public void setUploadDocumentsQuery(String uploadDocumentsQuery) {
		this.uploadDocumentsQuery = uploadDocumentsQuery;
	}
	private String showBatchIdDetails;

	public String getShowBatchIdDetails() {
		return showBatchIdDetails;
	}

	public void setShowBatchIdDetails(String showBatchIdDetails) {
		this.showBatchIdDetails = showBatchIdDetails;
	}
	private String showScgjDetails;
    private String batchidDetails;
	public String getBatchidDetails() {
		return batchidDetails;
	}

	public void setBatchidDetails(String batchidDetails) {
		this.batchidDetails = batchidDetails;
	}
	public String getShowScgjDetails() {
		return showScgjDetails;
	}
	public void setShowScgjDetails(String showScgjDetails) {
		this.showScgjDetails = showScgjDetails;
	}
	
}
