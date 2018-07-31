package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataImport",locations="classpath:sql/dataImport.yml")
public class DataImportConfig {

	private String BatchDetails;
	private String importCandidate;
	private String importBankDetails;
	private String checkCandidateExistance;
	private String updateExistingDetails;
	private String updateExistingBankDetails;
	
	

	public String getUpdateExistingDetails() {
		return updateExistingDetails;
	}
	public void setUpdateExistingDetails(String updateExistingDetails) {
		this.updateExistingDetails = updateExistingDetails;
	}
	public String getUpdateExistingBankDetails() {
		return updateExistingBankDetails;
	}
	public void setUpdateExistingBankDetails(String updateExistingBankDetails) {
		this.updateExistingBankDetails = updateExistingBankDetails;
	}
	public String getCheckCandidateExistance() {
		return checkCandidateExistance;
	}
	public void setCheckCandidateExistance(String checkCandidateExistance) {
		this.checkCandidateExistance = checkCandidateExistance;
	}
	public String getImportCandidate() {
		return importCandidate;
	}
	public void setImportCandidate(String importCandidate) {
		this.importCandidate = importCandidate;
	}
	public String getShowbatchId() {
		return showbatchId;
	}
	public void setShowbatchId(String showbatchId) {
		this.showbatchId = showbatchId;
	}
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
	
	
	private String checkCentreExistence;
	private String insertCentreDetails;
	private String updateCentreDetails;
	private String updateBatchDetails;

	public String getCheckCentreExistence() {
		return checkCentreExistence;
	}
	public void setCheckCentreExistence(String checkCentreExistence) {
		this.checkCentreExistence = checkCentreExistence;
	}
	public String getInsertCentreDetails() {
		return insertCentreDetails;
	}
	public void setInsertCentreDetails(String insertCentreDetails) {
		this.insertCentreDetails = insertCentreDetails;
	}
	public String getUpdateCentreDetails() {
		return updateCentreDetails;
	}
	public void setUpdateCentreDetails(String updateCentreDetails) {
		this.updateCentreDetails = updateCentreDetails;
	}
	public String getUpdateBatchDetails() {
		return updateBatchDetails;
	}
	public void setUpdateBatchDetails(String updateBatchDetails) {
		this.updateBatchDetails = updateBatchDetails;
	}
	public String getImportBankDetails() {
		return importBankDetails;
	}
	public void setImportBankDetails(String importBankDetails) {
		this.importBankDetails = importBankDetails;
	}
	
	

}
