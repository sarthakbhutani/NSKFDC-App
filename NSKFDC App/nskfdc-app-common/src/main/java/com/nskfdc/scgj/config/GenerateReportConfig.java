package com.nskfdc.scgj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="generateReport",locations="classpath:sql/generateReport.yml")
public class GenerateReportConfig {

	/* For Occupation Certificate */
	private String showOccupationCertificateReportDetails;
	
	/* For Attendance Sheet*/
	private String showAttendanceSheetDetails;
	
	/* For Updating Database*/
	private String updateGenerateReportsTable;
	
	/* For NSKFDC Excel Sheet*/
	private String showNSKFDCExcelSheet;
	
	/* For SDMS Excel Sheet*/
	private String showSDMSExcelSheet;
	
	/* For Displaying Audit Table Records*/
	private String showAuditTableRecords;
	
	public String getShowOccupationCertificateReportDetails() {
		return showOccupationCertificateReportDetails;
	}
	public void setShowOccupationCertificateReportDetails(String showOccupationCertificateReportDetails) {
		this.showOccupationCertificateReportDetails = showOccupationCertificateReportDetails;
	}

	public String getShowAttendanceSheetDetails() {
		return showAttendanceSheetDetails;
	}
	public void setShowAttendanceSheetDetails(String showAttendanceSheetDetails) {
		this.showAttendanceSheetDetails = showAttendanceSheetDetails;
	}
	
	public String getShowNSKFDCExcelSheet() {
		return showNSKFDCExcelSheet;
	}
	public void setShowNSKFDCExcelSheet(String showNSKFDCExcelSheet) {
		this.showNSKFDCExcelSheet = showNSKFDCExcelSheet;
	}

	public String getUpdateGenerateReportsTable() {
		return updateGenerateReportsTable;
	}
	public void setUpdateGenerateReportsTable(String updateGenerateReportsTable) {
		this.updateGenerateReportsTable = updateGenerateReportsTable;
	}	

	public String getShowAuditTableRecords() {
		return showAuditTableRecords;
	}
	public void setShowAuditTableRecords(String showAuditTableRecords) {
		this.showAuditTableRecords = showAuditTableRecords;
	}
	public String getShowSDMSExcelSheet() {
		return showSDMSExcelSheet;
	}
	public void setShowSDMSExcelSheet(String showSDMSExcelSheet) {
		this.showSDMSExcelSheet = showSDMSExcelSheet;
	}	
}
