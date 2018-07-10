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
	
	/* For NSKFDC Excel Sheet*/
	private String showNSKFDCExcelSheet;

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
}
