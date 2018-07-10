package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataImport",locations="classpath:sql/dataImport.yml")
public class DataImportConfig {

	//write your code here
	private String importBatchDetails;

	public String getImportBatchDetails() {
		return importBatchDetails;
	}

	public void setImportBatchDetails(String importBatchDetails) {
		this.importBatchDetails = importBatchDetails;
	}

	
}
