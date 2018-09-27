package com.nskfdc.scgj.dto;


import com.nskfdc.scgj.common.BaseDto;

public class UploadDocumentsDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchId;
	private String dateUploaded;
	private int finalBatchReport;
	private int occupationCertificate;
	private int minuteOfSelectionCommittee;
	private int dataSheetForSDDMS;
	private int dataSheetForNSKFC;
	private int attendanceSheet;
	private StringBuilder documentsUploaded;
	
	public StringBuilder getDocumentsUploaded() {
		return documentsUploaded;
	}
	public void setDocumentsUploaded(StringBuilder documentsUploaded) {
		this.documentsUploaded = documentsUploaded;
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
	public void setMinuteOfSelectionCommitteePath(
			String minuteOfSelectionCommitteePath) {
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
	private String finalBatchReportPath;
	private String occupationCertificatePath;
	private String minuteOfSelectionCommitteePath;
	private String dataSheetForSDMSPath;
    private String dataSheetForNSKFCPath;
	private String attendanceSheetPath;
	
	public int getFinalBatchReport() {
		return finalBatchReport;
	}
	public void setFinalBatchReport(int finalBatchReport) {
		this.finalBatchReport = finalBatchReport;
	}
	public int getOccupationCertificate() {
		return occupationCertificate;
	}
	public void setOccupationCertificate(int occupationCertificate) {
		this.occupationCertificate = occupationCertificate;
	}
	public int getMinuteOfSelectionCommittee() {
		return minuteOfSelectionCommittee;
	}
	public void setMinuteOfSelectionCommittee(int minuteOfSelectionCommittee) {
		this.minuteOfSelectionCommittee = minuteOfSelectionCommittee;
	}
	public int getDataSheetForSDDMS() {
		return dataSheetForSDDMS;
	}
	public void setDataSheetForSDDMS(int dataSheetForSDDMS) {
		this.dataSheetForSDDMS = dataSheetForSDDMS;
	}
	public int getDataSheetForNSKFC() {
		return dataSheetForNSKFC;
	}
	public void setDataSheetForNSKFC(int dataSheetForNSKFC) {
		this.dataSheetForNSKFC = dataSheetForNSKFC;
	}
	public int getAttendanceSheet() {
		return attendanceSheet;
	}
	public void setAttendanceSheet(int attendanceSheet) {
		this.attendanceSheet = attendanceSheet;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

		/**
	 * Default Constructor
	 */
	public UploadDocumentsDto() {
		super();
	}
		public UploadDocumentsDto(String batchId, String dateUploaded,
				int finalBatchReport, int occupationCertificate,
				int minuteOfSelectionCommittee, int dataSheetForSDDMS,
				int dataSheetForNSKFC, int attendanceSheet,
				String finalBatchReportPath, String occupationCertificatePath,
				String minuteOfSelectionCommitteePath,
				String dataSheetForSDMSPath, String dataSheetForNSKFCPath,
				String attendanceSheetPath, StringBuilder documentsUploaded) {
			super();
			this.batchId = batchId;
			this.dateUploaded = dateUploaded;
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
			this.documentsUploaded = documentsUploaded;
		}
	
	
	
	
}
