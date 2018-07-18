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
	
	
	//Author: Sagun Saluja
	
	private String ShowTotalTargets;
	
	public String getShowTotalTargets() {
		return ShowTotalTargets;
	}

	public void setShowTotalTargets(String ShowTotalTargets) {
		this.ShowTotalTargets = ShowTotalTargets;
	}


	


private String ShowTargetAchieved;
	
public String getShowTargetAchieved() {
		return ShowTargetAchieved;
}

public void setShowTargetAchieved(String ShowTargetAchieved) {
	this.ShowTargetAchieved = ShowTargetAchieved;
}

	
	

private String ShowRemainingTargets;

public String getShowRemainingTargets() {
	return ShowRemainingTargets;
}

public void setShowRemainingTargets(String showRemainingTargets) {
	ShowRemainingTargets = showRemainingTargets;
}
		
private String ShowFinancialYear;

public String getShowFinancialYear() {
	return ShowFinancialYear;
}

public void setShowFinancialYear(String showFinancialYear) {
	ShowFinancialYear = showFinancialYear;
}
  
	
}
