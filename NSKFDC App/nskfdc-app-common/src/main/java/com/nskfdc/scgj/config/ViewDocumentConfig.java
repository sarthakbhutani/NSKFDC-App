package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="viewDocumentQueryForSearchAndDownload",locations="classpath:sql/ViewDocument.yml")

public class ViewDocumentConfig {
	
	private String showTrainingPartnerDetailsForDownloadusingBatchNumber;
	private String showTrainingPartnerDetailsForDownloadusingBatchId;
	
	public String getShowTrainingPartnerDetailsForDownloadusingBatchNumber() {
		return showTrainingPartnerDetailsForDownloadusingBatchNumber;
	}
	public void setShowTrainingPartnerDetailsForDownloadusingBatchNumber(
			String showTrainingPartnerDetailsForDownloadusingBatchNumber) {
		this.showTrainingPartnerDetailsForDownloadusingBatchNumber = showTrainingPartnerDetailsForDownloadusingBatchNumber;
	}
	public String getShowTrainingPartnerDetailsForDownloadusingBatchId() {
		return showTrainingPartnerDetailsForDownloadusingBatchId;
	}
	public void setShowTrainingPartnerDetailsForDownloadusingBatchId(
			String showTrainingPartnerDetailsForDownloadusingBatchId) {
		this.showTrainingPartnerDetailsForDownloadusingBatchId = showTrainingPartnerDetailsForDownloadusingBatchId;
	}
	
	
	
	}
