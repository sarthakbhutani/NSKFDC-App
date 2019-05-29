package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataImport",locations="classpath:sql/dataImport.yml")
public class DataImportConfig {

	private String BatchDetails;
	private String nsdcRegNumber;
	private String checkMobileNumberExistence;
	private String checkCandidateExistence;
	private String importCandidate;
	private String importBankDetails;
	private String checkCandidate;
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
	private String updateBatchDetails;
	private String batchId;
	private String checkAadharNumberExistence;
	private String updateCheckAadharNumberExistence;
	private String updateCheckMobileNumberExistence;
	
	
	
	
	public String getUpdateCheckAadharNumberExistence() {
		return updateCheckAadharNumberExistence;
	}
	public void setUpdateCheckAadharNumberExistence(String updateCheckAadharNumberExistence) {
		this.updateCheckAadharNumberExistence = updateCheckAadharNumberExistence;
	}
	public String getUpdateCheckMobileNumberExistence() {
		return updateCheckMobileNumberExistence;
	}
	public void setUpdateCheckMobileNumberExistence(String updateCheckMobileNumberExistence) {
		this.updateCheckMobileNumberExistence = updateCheckMobileNumberExistence;
	}
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
		return checkCandidate;
	}
	public void setCheckCandidateExistance(String checkCandidateExistance) {
		this.checkCandidate = checkCandidateExistance;
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
	public String getUpdateBatchDetails() {
		return updateBatchDetails;
	}
	public void setUpdateBatchDetails(String updateBatchDetails) {
		this.updateBatchDetails = updateBatchDetails;
	}
	public String getCheckCandidateExistence() {
		return checkCandidateExistence;
	}
	public void setCheckCandidateExistence(String checkCandidateExistence) {
		this.checkCandidateExistence = checkCandidateExistence;
	}
	public String getCheckCandidate() {
		return checkCandidate;
	}
	public void setCheckCandidate(String checkCandidate) {
		this.checkCandidate = checkCandidate;
	}
	public String getNsdcRegNumber() {
		return nsdcRegNumber;
	}
	public void setNsdcRegNumber(String nsdcRegNumber) {
		this.nsdcRegNumber = nsdcRegNumber;
	}
	public String getCheckAadharNumberExistence() {
		return checkAadharNumberExistence;
	}
	public void setCheckAadharNumberExistence(String checkAadharNumberExistence) {
		this.checkAadharNumberExistence = checkAadharNumberExistence;
	}
	public String getCheckMobileNumberExistence() {
		return checkMobileNumberExistence;
	}
	public void setCheckMobileNumberExistence(String checkMobileNumberExistence) {
		this.checkMobileNumberExistence = checkMobileNumberExistence;
	}
	
	
	

	
	
	

}
