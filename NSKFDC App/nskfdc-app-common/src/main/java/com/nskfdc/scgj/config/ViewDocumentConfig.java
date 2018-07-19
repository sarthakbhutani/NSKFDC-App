package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="viewDocumentQueryForSearchAndDownload",locations="classpath:sql/ViewDocument.yml")

public class ViewDocumentConfig {
	private String  showTrainingPartnerDetailsForDownload;

	public String getShowTrainingPartnerDetailsForDownload(){
		return showTrainingPartnerDetailsForDownload;
	}

	public void setShowTrainingPartnerDetailsForDownload(
			String showTrainingPartnerDetailsForDownload) {
		this.showTrainingPartnerDetailsForDownload = showTrainingPartnerDetailsForDownload;
	}
	
	}
