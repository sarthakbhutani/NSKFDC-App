package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataImport",locations="classpath:sql/dataImport.yml")
public class DataImportConfig {

	private String BatchDetails;

	public String getBatchDetails() {
		return BatchDetails;
	}
	public void setBatchDetails(String BatchDetails) {
		this.BatchDetails = BatchDetails;
	}	
	
	private String downloadMasterSheet;

	public String getDownloadMasterSheet() {
		return downloadMasterSheet;
	}

	public void setDownloadMasterSheet(String downloadMasterSheet) {
		this.downloadMasterSheet = downloadMasterSheet;
	}
	
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
