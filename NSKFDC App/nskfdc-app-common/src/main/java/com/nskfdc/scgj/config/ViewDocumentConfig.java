package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="viewDocumentQueryBatchId",locations="classpath:sql/ViewDocumentBatchId.yml")

public class ViewDocumentConfig {
	private String  showTrainingPartnerDetailsForBatchId;
	/*private String showTrainingPartnerDetailsForBatchId;*/

		public String getShowTrainingPartnerDetailsForBatchId() {
		return showTrainingPartnerDetailsForBatchId;
	}

	public void setShowTrainingPartnerDetailsForBatchId(
			String showTrainingPartnerDetailsForBatchId) {
		this.showTrainingPartnerDetailsForBatchId = showTrainingPartnerDetailsForBatchId;
	}
	
	/*public String getShowTrainingPartnerDetailsForscgjBatchNumber() {
		return showTrainingPartnerDetailsForscgjBatchNumber;
	}

	public void setShowTrainingPartnerDetailsForscgjBatchNumber(
			String showTrainingPartnerDetailsForscgjBatchNumber) {
		this.showTrainingPartnerDetailsForscgjBatchNumber = showTrainingPartnerDetailsForscgjBatchNumber;
	}*/

		
	
	
}
