package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="uploadDocuments",locations="classpath:sql/uploadDocuments.yml")
public class UploadDocumentConfig {
	
	private String insertIntoUploadDocument;
	private String checkExistence;
	private String insertUserDetails;
	private String updateOccupationCertificatePath;
	private String updateFinalBatchReportPath;
	private String updateAttendanceSheetPath;
	private String updateNSKFDCSheet;
    private String updateSDMSSheet;
    private String updateMinuteOfSelectionCommittee;
    

	
	public String getCheckExistence() {
		return checkExistence;
	}

	public void setCheckExistence(String checkExistence) {
		this.checkExistence = checkExistence;
	}

	public String getInsertUserDetails() {
		return insertUserDetails;
	}

	public void setInsertUserDetails(String insertUserDetails) {
		this.insertUserDetails = insertUserDetails;
	}

	public String getUpdateOccupationCertificatePath() {
		return updateOccupationCertificatePath;
	}

	public void setUpdateOccupationCertificatePath(String updateOccupationCertificatePath) {
		this.updateOccupationCertificatePath = updateOccupationCertificatePath;
	}

	public String getUpdateFinalBatchReportPath() {
		return updateFinalBatchReportPath;
	}

	public void setUpdateFinalBatchReportPath(String updateFinalBatchReportPath) {
		this.updateFinalBatchReportPath = updateFinalBatchReportPath;
	}

	public String getUpdateAttendanceSheetPath() {
		return updateAttendanceSheetPath;
	}

	public void setUpdateAttendanceSheetPath(String updateAttendanceSheetPath) {
		this.updateAttendanceSheetPath = updateAttendanceSheetPath;
	}

	public String getUpdateNSKFDCSheet() {
		return updateNSKFDCSheet;
	}

	public void setUpdateNSKFDCSheet(String updateNSKFDCSheet) {
		this.updateNSKFDCSheet = updateNSKFDCSheet;
	}

	public String getUpdateSDMSSheet() {
		return updateSDMSSheet;
	}

	public void setUpdateSDMSSheet(String updateSDMSSheet) {
		this.updateSDMSSheet = updateSDMSSheet;
	}

	public String getUpdateMinuteOfSelectionCommittee() {
		return updateMinuteOfSelectionCommittee;
	}

	public void setUpdateMinuteOfSelectionCommittee(String updateMinuteOfSelectionCommittee) {
		this.updateMinuteOfSelectionCommittee = updateMinuteOfSelectionCommittee;
	}
	private String uploadDocumentsQuery;

	public String getUploadDocumentsQuery() {
		return uploadDocumentsQuery;
	}

	public void setUploadDocumentsQuery(String uploadDocumentsQuery) {
		this.uploadDocumentsQuery = uploadDocumentsQuery;
	}
	private String showBatchIdDetails;

	public String getShowBatchIdDetails() {
		return showBatchIdDetails;
	}

	public void setShowBatchIdDetails(String showBatchIdDetails) {
		this.showBatchIdDetails = showBatchIdDetails;
	}
	private String showScgjDetails;
    private String batchidDetails;
	public String getBatchidDetails() {
		return batchidDetails;
	}

	public void setBatchidDetails(String batchidDetails) {
		this.batchidDetails = batchidDetails;
	}
	public String getShowScgjDetails() {
		return showScgjDetails;
	}
	public void setShowScgjDetails(String showScgjDetails) {
		this.showScgjDetails = showScgjDetails;
	}

	public String getInsertIntoUploadDocument() {
		return insertIntoUploadDocument;
	}

	public void setInsertIntoUploadDocument(String insertIntoUploadDocument) {
		this.insertIntoUploadDocument = insertIntoUploadDocument;
	}
	
}
