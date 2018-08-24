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
	private String checkBankExistence;
	private String updateExistingDetails;
	private String updateExistingBankDetails;
	private String candidateSheet;	
	private String insertBatchId;
	private String showTpName;
	private String numberOfBatches;
	private String downloadMasterSheet;
	private String ShowTotalTargets;
    private String ShowTargetAchieved;
	private String ShowFinancialYear;
	private String generateBatch;
	private String showbatchId;
	private String checkCentreExistence;
	private String insertCentreDetails;
	private String updateCentreDetails;
	private String updateBatchDetails;
	private String batchId;
	
	
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getBatchDetails() {
		return BatchDetails;
	}
	public void setBatchDetails(String batchDetails) {
		BatchDetails = batchDetails;
	}
	public String getImportCandidate() {
		return importCandidate;
	}
	public void setImportCandidate(String importCandidate) {
		this.importCandidate = importCandidate;
	}
	public String getImportBankDetails() {
		return importBankDetails;
	}
	public void setImportBankDetails(String importBankDetails) {
		this.importBankDetails = importBankDetails;
	}
	public String getCheckCandidateExistance() {
		return checkCandidateExistance;
	}
	public void setCheckCandidateExistance(String checkCandidateExistance) {
		this.checkCandidateExistance = checkCandidateExistance;
	}
	public String getCheckBankExistence() {
		return checkBankExistence;
	}
	public void setCheckBankExistence(String checkBankExistence) {
		this.checkBankExistence = checkBankExistence;
	}
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
	public String getCandidateSheet() {
		return candidateSheet;
	}
	public void setCandidateSheet(String candidateSheet) {
		this.candidateSheet = candidateSheet;
	}
	public String getInsertBatchId() {
		return insertBatchId;
	}
	public void setInsertBatchId(String insertBatchId) {
		this.insertBatchId = insertBatchId;
	}
	public String getShowTpName() {
		return showTpName;
	}
	public void setShowTpName(String showTpName) {
		this.showTpName = showTpName;
	}
	public String getNumberOfBatches() {
		return numberOfBatches;
	}
	public void setNumberOfBatches(String numberOfBatches) {
		this.numberOfBatches = numberOfBatches;
	}
	public String getDownloadMasterSheet() {
		return downloadMasterSheet;
	}
	public void setDownloadMasterSheet(String downloadMasterSheet) {
		this.downloadMasterSheet = downloadMasterSheet;
	}
	public String getShowTotalTargets() {
		return ShowTotalTargets;
	}
	public void setShowTotalTargets(String showTotalTargets) {
		ShowTotalTargets = showTotalTargets;
	}
	public String getShowTargetAchieved() {
		return ShowTargetAchieved;
	}
	public void setShowTargetAchieved(String showTargetAchieved) {
		ShowTargetAchieved = showTargetAchieved;
	}
	public String getShowFinancialYear() {
		return ShowFinancialYear;
	}
	public void setShowFinancialYear(String showFinancialYear) {
		ShowFinancialYear = showFinancialYear;
	}
	public String getGenerateBatch() {
		return generateBatch;
	}
	public void setGenerateBatch(String generateBatch) {
		this.generateBatch = generateBatch;
	}
	public String getShowbatchId() {
		return showbatchId;
	}
	public void setShowbatchId(String showbatchId) {
		this.showbatchId = showbatchId;
	}
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
	public DataImportConfig(String batchDetails, String importCandidate, String importBankDetails,
			String checkCandidateExistance, String checkBankExistence, String updateExistingDetails,
			String updateExistingBankDetails, String candidateSheet, String insertBatchId, String showTpName,
			String numberOfBatches, String downloadMasterSheet, String showTotalTargets, String showTargetAchieved,
			String showFinancialYear, String generateBatch, String showbatchId, String checkCentreExistence,
			String insertCentreDetails, String updateCentreDetails, String updateBatchDetails, String batchId) {
		super();
		BatchDetails = batchDetails;
		this.importCandidate = importCandidate;
		this.importBankDetails = importBankDetails;
		this.checkCandidateExistance = checkCandidateExistance;
		this.checkBankExistence = checkBankExistence;
		this.updateExistingDetails = updateExistingDetails;
		this.updateExistingBankDetails = updateExistingBankDetails;
		this.candidateSheet = candidateSheet;
		this.insertBatchId = insertBatchId;
		this.showTpName = showTpName;
		this.numberOfBatches = numberOfBatches;
		this.downloadMasterSheet = downloadMasterSheet;
		ShowTotalTargets = showTotalTargets;
		ShowTargetAchieved = showTargetAchieved;
		ShowFinancialYear = showFinancialYear;
		this.generateBatch = generateBatch;
		this.showbatchId = showbatchId;
		this.checkCentreExistence = checkCentreExistence;
		this.insertCentreDetails = insertCentreDetails;
		this.updateCentreDetails = updateCentreDetails;
		this.updateBatchDetails = updateBatchDetails;
		this.batchId = batchId;
	}
	public DataImportConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	

	
	


}
