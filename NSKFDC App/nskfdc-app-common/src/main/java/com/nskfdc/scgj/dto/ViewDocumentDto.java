package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class ViewDocumentDto extends BaseDto{
	/**
	 * This is the DTO to store 
	 * the values retrieved from database
	 * to show SCGJ the search results for documents
	 * uploaded by Training Partner 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String trainingPartnerName;
	private String uplodedOn;
	private Integer finalBatchReport;
	private Integer occupationCertificate;
	private Integer minuteOfSelectionCommittee;
	private Integer dataSheetForSDDMS;
	private Integer dataSheetForNSKFC;
	private Integer attendanceSheet;
	private String finalBatchReportPath;
	private String occupationCertificatePath;
	private String minuteOfSelectionCommitteePath;
	private String dataSheetForSDMSPath;
	private String dataSheetForNSKFCPath;
	private String attendanceSheetPath;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getTrainingPartnerName() {
		return trainingPartnerName;
	}
	public void setTrainingPartnerName(String trainingPartnerName) {
		this.trainingPartnerName = trainingPartnerName;
	}
	public String getUplodedOn() {
		return uplodedOn;
	}
	public void setUplodedOn(String uplodedOn) {
		this.uplodedOn = uplodedOn;
	}
	public Integer getFinalBatchReport() {
		return finalBatchReport;
	}
	public void setFinalBatchReport(Integer finalBatchReport) {
		this.finalBatchReport = finalBatchReport;
	}
	public Integer getOccupationCertificate() {
		return occupationCertificate;
	}
	public void setOccupationCertificate(Integer occupationCertificate) {
		this.occupationCertificate = occupationCertificate;
	}
	public Integer getMinuteOfSelectionCommittee() {
		return minuteOfSelectionCommittee;
	}
	public void setMinuteOfSelectionCommittee(Integer minuteOfSelectionCommittee) {
		this.minuteOfSelectionCommittee = minuteOfSelectionCommittee;
	}
	public Integer getDataSheetForSDDMS() {
		return dataSheetForSDDMS;
	}
	public void setDataSheetForSDDMS(Integer dataSheetForSDDMS) {
		this.dataSheetForSDDMS = dataSheetForSDDMS;
	}
	public Integer getDataSheetForNSKFC() {
		return dataSheetForNSKFC;
	}
	public void setDataSheetForNSKFC(Integer dataSheetForNSKFC) {
		this.dataSheetForNSKFC = dataSheetForNSKFC;
	}
	public Integer getAttendanceSheet() {
		return attendanceSheet;
	}
	public void setAttendanceSheet(Integer attendanceSheet) {
		this.attendanceSheet = attendanceSheet;
	}
	public String getFinalBatchReportPath() {
		return finalBatchReportPath;
	}
	public void setFinalBatchReportPath(String finalBatchReportPath) {
		this.finalBatchReportPath = finalBatchReportPath;
	}
	public String getOccupationCertificatePath() {
		return occupationCertificatePath;
	}
	public void setOccupationCertificatePath(String occupationCertificatePath) {
		this.occupationCertificatePath = occupationCertificatePath;
	}
	public String getMinuteOfSelectionCommitteePath() {
		return minuteOfSelectionCommitteePath;
	}
	public void setMinuteOfSelectionCommitteePath(String minuteOfSelectionCommitteePath) {
		this.minuteOfSelectionCommitteePath = minuteOfSelectionCommitteePath;
	}
	public String getDataSheetForSDMSPath() {
		return dataSheetForSDMSPath;
	}
	public void setDataSheetForSDMSPath(String dataSheetForSDMSPath) {
		this.dataSheetForSDMSPath = dataSheetForSDMSPath;
	}
	public String getDataSheetForNSKFCPath() {
		return dataSheetForNSKFCPath;
	}
	public void setDataSheetForNSKFCPath(String dataSheetForNSKFCPath) {
		this.dataSheetForNSKFCPath = dataSheetForNSKFCPath;
	}
	public String getAttendanceSheetPath() {
		return attendanceSheetPath;
	}
	public void setAttendanceSheetPath(String attendanceSheetPath) {
		this.attendanceSheetPath = attendanceSheetPath;
	}
	public ViewDocumentDto(String batchId, String trainingPartnerName, String uplodedOn, Integer finalBatchReport,
			Integer occupationCertificate, Integer minuteOfSelectionCommittee, Integer dataSheetForSDDMS,
			Integer dataSheetForNSKFC, Integer attendanceSheet, String finalBatchReportPath,
			String occupationCertificatePath, String minuteOfSelectionCommitteePath, String dataSheetForSDMSPath,
			String dataSheetForNSKFCPath, String attendanceSheetPath) {
		super();
		this.batchId = batchId;
		this.trainingPartnerName = trainingPartnerName;
		this.uplodedOn = uplodedOn;
		this.finalBatchReport = finalBatchReport;
		this.occupationCertificate = occupationCertificate;
		this.minuteOfSelectionCommittee = minuteOfSelectionCommittee;
		this.dataSheetForSDDMS = dataSheetForSDDMS;
		this.dataSheetForNSKFC = dataSheetForNSKFC;
		this.attendanceSheet = attendanceSheet;
		this.finalBatchReportPath = finalBatchReportPath;
		this.occupationCertificatePath = occupationCertificatePath;
		this.minuteOfSelectionCommitteePath = minuteOfSelectionCommitteePath;
		this.dataSheetForSDMSPath = dataSheetForSDMSPath;
		this.dataSheetForNSKFCPath = dataSheetForNSKFCPath;
		this.attendanceSheetPath = attendanceSheetPath;
	}
	
	public ViewDocumentDto() {
		super();
	}

	
	
	
	
}
	
	