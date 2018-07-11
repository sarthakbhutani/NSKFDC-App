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
	
	private String showReport;

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

	public String getShowReport() {
		return showReport;
	}
	public void setShowReport(String showReport) {
		this.showReport = showReport;
	}
    	
}
