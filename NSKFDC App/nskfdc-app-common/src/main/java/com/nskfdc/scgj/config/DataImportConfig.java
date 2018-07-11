package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataImport",locations="classpath:sql/dataImport.yml")
public class DataImportConfig {

	//write your code here
	private String generateBatch;

	public String getGenerateBatch() {
		return generateBatch;
	}

	public void setGenerateBatch(String generateBatch) {
		this.generateBatch = generateBatch;
	}
	private String showbatchId;

	public String getshowbatchId() {
		return showbatchId;
	}

	public void setshowbatchId(String showbatchId) {
		this.showbatchId = showbatchId;
	}

  
	
}
