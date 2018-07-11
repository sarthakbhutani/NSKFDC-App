package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="uploadDocuments",locations="classpath:sql/uploadDocuments.yml")
public class UploadDocumentConfig {

	private String showBatchIdDetails;

	public String getShowBatchIdDetails() {
		return showBatchIdDetails;
	}

	public void setShowBatchIdDetails(String showBatchIdDetails) {
		this.showBatchIdDetails = showBatchIdDetails;
	}
	
	
}
